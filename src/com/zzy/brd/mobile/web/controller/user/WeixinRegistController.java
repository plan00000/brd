package com.zzy.brd.mobile.web.controller.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.Information;
import com.zzy.brd.entity.SmsAuthcode;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.Information.InformationType;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.entity.WeixinUser;
import com.zzy.brd.entity.WeixinUser.SexType;
import com.zzy.brd.entity.WeixinUser.SubscribeType;
import com.zzy.brd.enums.SmsAuthcodeSource;
import com.zzy.brd.mobile.web.dto.req.user.ReqUserRegisterDTO;
import com.zzy.brd.service.InformationService;
import com.zzy.brd.service.UserService;
import com.zzy.brd.service.WeixinUserService;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.phone.PhoneUtils;
import com.zzy.brd.util.string.StringUtil;

/**
 * 注册
 *
 * @author lzh 2016/9/22
 */
@Controller
@RequestMapping("weixin/user/register")
public class WeixinRegistController {
    @Autowired
    private UserService userService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private WeixinUserService weixinUserService;

    /**
     * 跳转到注册页面
     *
     * @param model
     * @return
     */
    @RequestMapping("toRegister")
    public String toRegisterPage(Model model
            , @RequestParam(value = "openId", required = false, defaultValue = "") String openId) {
        model.addAttribute("openId", openId);
        return "mobile/user/register";
    }

    /**
     * 跳转到服务协议
     *
     * @param model
     * @return
     */
    @RequestMapping("toServiceAgreement")
    public String toServiceAgreement(Model model) {
        Information agreement = informationService.findInformationByType(InformationType.AGREEMENT);
        model.addAttribute("agreement", agreement);
        return "mobile/user/serviceAgreement";
    }

    /**
     * 判断注册手机是否可用
     *
     * @param phone
     * @param request
     * @return
     */
    @RequestMapping("isPhoneRegister")
    @ResponseBody
    public RepSimpleMessageDTO isPhoneRegister(String phone, HttpServletRequest request) {
        RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
        List<User> users = userService.findByMobileno1(phone);
        if (users.isEmpty()) {
            rep.setCode(1);
            rep.setMes("success");
            return rep;
        } else {
            for (User user : users) {
                if (user.getUserType().ordinal() == 0 || user.getUserType().ordinal() == 1 || user.getUserType().ordinal() == 2 || user.getUserType().ordinal() == 4) {
                    rep.setCode(0);
                    rep.setMes("该手机号已被注册");
                    return rep;
                }
            }
            rep.setCode(1);
            rep.setMes("success");
            return rep;
        }
    }

    /**
     * 获取短信注册验证码
     *
     * @param phone
     * @param request
     * @return
     */
    @RequestMapping("getPhoneAuthcode/register")
    @ResponseBody
    public RepSimpleMessageDTO getPhoneAuthcode(String phone, HttpServletRequest request) {
        RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
        System.out.println("注册获取验证码phone:" + phone);
        //判断手机号是不是为福建手机号
        if (!PhoneUtils.getPhoneProv(phone.trim()).contains("福建")) {
            rep.setCode(0);
            rep.setMes("注册手机号只能是福建省手机号");
            return rep;
        }
        SmsAuthcodeSource smsAuthcodeSource = SmsAuthcodeSource.USER_REG;
        if (userService.sendAuthcode(phone, smsAuthcodeSource)) {
            rep.setCode(1);
            rep.setMes("success");
            return rep;
        }
        rep.setCode(0);
        rep.setMes("验证码发送失败");
        return rep;
    }

    /**
     * 注册
     *
     * @param reqUserRegisterDTO
     * @param result
     * @param request
     * @return
     */
    @RequestMapping("userRegister")
    @ResponseBody
    public RepSimpleMessageDTO userRegister(@Valid ReqUserRegisterDTO reqUserRegisterDTO
            , BindingResult result, HttpServletRequest request
            , @RequestParam(value = "openId", required = false, defaultValue = "") String openId) {
        RepSimpleMessageDTO rep = new RepSimpleMessageDTO();
        //验证手机号是否已被注册了
        /*User user =userService.findByMobileno(reqUserRegisterDTO.getMobileno());
        if(user!=null){
			rep.setMes("该手机号已被注册");
			rep.setCode(0);
			return rep;
		}*/
        List<User> users = userService.findByMobileno1(reqUserRegisterDTO.getMobileno());
        if (users.isEmpty()) {

        } else {
            for (User user : users) {
                if (user.getUserType().ordinal() == 0 || user.getUserType().ordinal() == 1 || user.getUserType().ordinal() == 2 || user.getUserType().ordinal() == 4) {
                    rep.setCode(0);
                    rep.setMes("该手机号已被注册");
                    return rep;
                }
            }
        }
        //判断手机号是否为福建手机号，
        String place = PhoneUtils.getPhoneProv(reqUserRegisterDTO.getMobileno());
        if (place.equals("未知") || place.length() == 0) {
            rep.setCode(0);
            rep.setMes("不存在该手机号，请填写正确的手机号");
            return rep;
        }
        //判断手机号是不是为福建手机号
        if (!place.contains("福建")) {
            rep.setCode(0);
            rep.setMes("注册手机号只能是福建省手机号");
            return rep;
        }
        //判断验证码是否有效
        int v = userService.validateSmsAuthcode(reqUserRegisterDTO.getMobileno(),
                reqUserRegisterDTO.getPhoneAuthcode(), SmsAuthcodeSource.USER_REG);
        if (v == 1 || v == 2) {
            rep.setCode(0);
            rep.setMes("手机验证码错误");
            return rep;
        }
        if (v == 3) {
            rep.setCode(0);
            rep.setMes("手机验证码过期");
            return rep;
        }
        //判断推荐码是否有效
        if (!StringUtil.isNullString(reqUserRegisterDTO.getAskperson())) {
            User askUser = userService.findByMobileno(reqUserRegisterDTO.getAskperson());
            //推荐码不存在
            if (askUser == null) {
                rep.setCode(0);
                rep.setMes("您输入的师傅推荐号码不存在，请确认后输入 ");
                return rep;
            }
            if (askUser.getState() == State.DEL) {
                rep.setCode(0);
                rep.setMes("您输入的师傅推荐号码不存在，请确认后输入");
                return rep;
            }

        }
        User newUser = new User();
        newUser.setMobileno(reqUserRegisterDTO.getMobileno());
        newUser.setUsername(reqUserRegisterDTO.getUsername());
        newUser.setCreatedate(new Date());
        newUser.setPassword(reqUserRegisterDTO.getPassword().toLowerCase());
        newUser.setState(State.ON);
        newUser.setUserType(UserType.USER);

        WeixinUser weixinUser = new WeixinUser();
        if (openId != null && openId.length() != 0) {
            // 如果已存在相应的微信用户, 不再重复注册
            WeixinUser orgUser = weixinUserService.findUserByOpenId(openId);

            if (orgUser == null) {
                weixinUser.setOpenid(openId);
                weixinUser.setSubscribe(SubscribeType.NO);
                weixinUser.setSubscribeTime(DateUtil.getNowDate());
                weixinUser.setSex(SexType.FEMALE);
                boolean a = weixinUserService.editUser(weixinUser);
                if (!a) {
                    rep.setCode(0);
                    rep.setMes("openId存入失败");
                    return rep;
                }
            }
            else {
                weixinUser = orgUser;
            }
        }
        newUser.setWeixinUser(weixinUser);

        //推荐人不为空的情况
        User parentUser = null;
        if (!StringUtil.isNullString(reqUserRegisterDTO.getAskperson())) {
            User parent = userService.findByAskperson(reqUserRegisterDTO.getAskperson());
            if (parent != null) {
                parentUser = parent;
            } else {
                rep.setCode(0);
                rep.setMes("推荐人不存在");
                return rep;
            }
        }
        if (userService.register(newUser, parentUser)) {
            rep.setCode(1);
            rep.setMes("注册成功");
            return rep;
        }
        return rep;
    }

}
