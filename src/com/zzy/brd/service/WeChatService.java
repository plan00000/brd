package com.zzy.brd.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.zzy.brd.entity.*;
import com.zzy.brd.util.weixin.WeixinCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.zzy.brd.controller.weixin.WeChatController;
import com.zzy.brd.util.weixin.WechatMessageUtil;

/**
 * weixin
 * @author lzh 2016/12/20
 *
 */
@Service
public class WeChatService {
    private static Logger log = LoggerFactory.getLogger(WeChatService.class);
    @Autowired
    private QrcodeActivityService qrcodeActivityService;

    @Autowired
    private WeixinUserService weixinUserService;

    @Autowired
    private SysInfoService sysInfoService;

    /*@Autowired
    private WeixinScanRecordService weixinScanRecordService;*/

    public String processRequest(HttpServletRequest request) {
        Map<String, String> map = WechatMessageUtil.xmlToMap(request);
//        log.info(map);
        // 发送方帐号（一个OpenID）
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        //消息创建时间
        String createTime = map.get("CreateTime");
        // 消息类型
        String msgType = map.get("MsgType");
        // 事件类型
        String event = map.get("Event");
        // 事件KEY值
        String eventKey = map.get("EventKey");
        // 二维码的ticket
        String ticket = map.get("Ticket");

        // 默认回复一个"success"
        String responseMessage = "success";

        SysInfo sysInfo = sysInfoService.getSysInfo(1l);
        // 对关注进行处理
        if ("event".equals(msgType.toLowerCase()) && !fromUserName.equals("")) {// 文本消息
            event = event.toLowerCase();
            // 关注事件
            if (WechatMessageUtil.MESSAGE_EVENT_SUBSCRIBE.equals(event)) {
                // 查询数据库, 是否已有该用户资料
                WeixinUser user = weixinUserService.findUserByOpenId(fromUserName);

                if (user == null) {
                    user = new WeixinUser();
                    WeixinCommonUtil.getPersonInformation(fromUserName, sysInfo.getAppid(), sysInfo.getAppsecret(), user);
                    user.setOpenid(fromUserName);

                    if (user.getNickname() == null) {
                        throw new RuntimeException("请求微信用户信息失败: " + fromUserName);
                    }
                }
                // 如果数据为空, 重新请求数据
                else if (user.getNickname() == null){
                   WeixinCommonUtil.getPersonInformation(fromUserName, sysInfo.getAppid(), sysInfo.getAppsecret(), user);
                    if (user.getNickname() == null) {
                        throw new RuntimeException("请求微信用户信息失败: " + fromUserName);
                    }
                }

                user.setSubscribeTime(new Date());
                user.setSubscribe(WeixinUser.SubscribeType.YES);

                // 是否是扫码关注
                if (eventKey != null && eventKey.startsWith("qrscene_")) {
                    //统计不同二维码被扫描的次数
                    String scene = eventKey.substring(8);
                    long sceneId = Long.parseLong(scene);
                    QrcodeActivity qrcodeActivity = qrcodeActivityService.findById(sceneId);
                    qrcodeActivity.setScanNum(qrcodeActivity.getScanNum() + 1);
                    qrcodeActivity.setConcernNum(qrcodeActivity.getConcernNum() + 1);
                    qrcodeActivityService.addAndEdit(qrcodeActivity);

                    // 开始按天统计扫码量
                    this.countScanRecord(sceneId, true);

                    // 场景值id更新
                    List<String> ids;
                    try {
                        ids = JSON.parseArray(user.getSceneids(), String.class);
                        if (ids == null) {
                            ids = new ArrayList<>();
                        }
                    }
                    catch (Exception e) {
                        ids = new ArrayList<>();
                    }
                    if (ids.contains(scene)) {
                        ids.remove(scene);
                    }
                    ids.add(scene);
                    user.setSceneids(JSON.toJSONString(ids));
                }
                weixinUserService.editUser(user);
            }
            // 扫码事件
            else if (WechatMessageUtil.MESSAGE_EVENT_SCAN.equals(event)) {
                QrcodeActivity qrcodeActivity = qrcodeActivityService.findById(Integer.parseInt(eventKey));
                qrcodeActivity.setScanNum(qrcodeActivity.getScanNum() + 1);
                qrcodeActivityService.addAndEdit(qrcodeActivity);
                this.countScanRecord(Long.parseLong(eventKey), false);
            }
            // 取消关注事件
            else if (WechatMessageUtil.MESSAGE_EVENT_UNSUBSCRIBE.equals(event)) {
                WeixinUser user = weixinUserService.findUserByOpenId(fromUserName);
                if (user != null) {
                    user.setSubscribe(WeixinUser.SubscribeType.NO);
                    weixinUserService.editUser(user);
                }
            }
        }
        log.info(responseMessage);
        return responseMessage;

    }

    /**
     * 按天统计一次扫码量
     * @param sceneId 场景id
     * @param isSubscribe  是否关注
     */
    private void countScanRecord(long sceneId, boolean isSubscribe) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        /*WeixinScanRecord record = weixinScanRecordService.findRecordByDate(sceneId, df.format(new Date()));
        if (record == null) {
            record = new WeixinScanRecord();
            record.setConcernNum(1);
            record.setScanNum(isSubscribe ? 1 : 0);
            record.setSceneId(sceneId);
            record.setDate(df.format(new Date()));
        }
        else {
            record.setScanNum(record.getScanNum() + 1);
            if (isSubscribe) {
                record.setConcernNum(record.getConcernNum() + 1);
            }
        }
        weixinScanRecordService.editRecord(record);*/
    }
}
