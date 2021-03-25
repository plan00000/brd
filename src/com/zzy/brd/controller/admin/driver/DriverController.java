package com.zzy.brd.controller.admin.driver;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dto.rep.RepSimpleMessageDTO;
import com.zzy.brd.entity.TbDriver;
import com.zzy.brd.entity.TbOrder;
import com.zzy.brd.service.DriverService;
import com.zzy.brd.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wpr on 2021/3/23 0023.
 */
@Controller
@RequestMapping("admin/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @RequestMapping("list")
    public String list(@RequestParam(value = "page",required = true,defaultValue = "1") int pageNum
            , @RequestParam(value = "status",required = false,defaultValue = "") String status
            , @RequestParam(value = "sortName",required = false,defaultValue = "") String sortName
            , @RequestParam(value = "sortType",required = false,defaultValue = "") String sortType
            , @RequestParam(value = "searchName",required = false,defaultValue ="") String searchName
            , @RequestParam(value = "searchValue",required = false,defaultValue = "") String searchValue
            , HttpServletRequest request, Model model){
        Map<String,Object> searchParams = new HashMap<String, Object>();
        if(!StringUtils.isBlank(status)){
            if("0".equals(status)){
                searchParams.put("EQ_driverStatus", 0);
            }
            if("1".equals(status)){
                searchParams.put("EQ_driverStatus", 1);
            }
        }

        if(!StringUtils.isBlank(searchName)){
            if("userName".equals(searchName)){
                String search = "LIKE_userName";
                searchParams.put(search, searchValue);
            }
            if("carNo".equals(searchName)){
                String search = "LIKE_carNo";
                searchParams.put(search, searchValue);
            }
            if("idCard".equals(searchName)){
                String search = "LIKE_idCard";
                searchParams.put(search, searchValue);
            }

        }

        Page<TbDriver> driverforms = driverService.adminDriverformList(searchParams, sortName, sortType, pageNum, Constant.PAGE_SIZE);
        model.addAttribute("driverforms", driverforms);
        model.addAttribute("status", status);
        model.addAttribute("sortName", sortName);
        model.addAttribute("sortType", sortType);
        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("page", pageNum);
        model.addAttribute("queryStr", request.getQueryString());
        model.addAttribute("totalcount", driverforms.getTotalElements());
        return "admin/driver/driverlist";
    }

    /**
     * 跳转到新增司机页面
     * @return
     */
    @RequestMapping(value = "toAddDriver")
    public String toAddDriver() {

        return "admin/driver/addDriver";
    }

    @RequestMapping(value = "addDriver")
    @ResponseBody
    public RepSimpleMessageDTO addDriver(
            String phone,String password,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "idCard", required = false) String idCard,
            @RequestParam(value = "carNo", required = false) String carNo,
            @RequestParam(value = "driverNo", required = false) String driverNo,
            @RequestParam(value = "carMark", required = false) String carMark,
            @RequestParam(value = "carColor",required=false)String carColor,
            HttpServletRequest request) {
        TbDriver tbDriver = new TbDriver();
        tbDriver.setMobileno(phone);
        tbDriver.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        tbDriver.setUserName(name);
        tbDriver.setCarNo(carNo);
        tbDriver.setIdCard(idCard);
        tbDriver.setDriverNo(driverNo);
        tbDriver.setCarMark(carMark);
        tbDriver.setCarColor(carColor);
        RepSimpleMessageDTO res = driverService.addDriver(tbDriver);
        return res;
    }

    /**
     * 跳转到编辑页面
     * @param pageNumber
     * @param type
     * * @param model
     * @return
     */
    @RequestMapping(value = "toEditDriver/{driverId}")
    public String toEditDriver(
            @PathVariable long driverId,
            @RequestParam(value = "page", required = true, defaultValue = "1") int pageNumber,
            @RequestParam(value = "type", required = false, defaultValue = "brokerage") String type,
            Model model) {
        TbDriver tbDriver = driverService.findById(driverId);

        model.addAttribute("tbDriver", tbDriver);
        model.addAttribute("type", type);
        return "admin/driver/editDriver";
    }

    /**
     * 编辑
     * @param phone
     * @param password
     * @param id
     * @param name
     * @param idCard
     * @param carNo
     * @param driverNo
     * @param carMark
     * @param carColor
     * @param request
     * @return
     */
    @RequestMapping(value = "editDriver")
    @ResponseBody
    public RepSimpleMessageDTO editDriver(
            String phone,String password,
            @RequestParam(value="id",required = true) long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "idCard", required = false) String idCard,
            @RequestParam(value = "carNo", required = false) String carNo,
            @RequestParam(value = "driverNo", required = false) String driverNo,
            @RequestParam(value = "carMark", required = false) String carMark,
            @RequestParam(value = "carColor",required=false)String carColor,
            HttpServletRequest request) {
        TbDriver tbDriver = new TbDriver();
        tbDriver.setId(id);
        tbDriver.setMobileno(phone);
        tbDriver.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        tbDriver.setUserName(name);
        tbDriver.setCarNo(carNo);
        tbDriver.setIdCard(idCard);
        tbDriver.setDriverNo(driverNo);
        tbDriver.setCarMark(carMark);
        tbDriver.setCarColor(carColor);
        RepSimpleMessageDTO res = driverService.editDriver(tbDriver);
        return res;
    }

}
