package com.zzy.brd.service;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.ConfigSetting.PathType;
import com.zzy.brd.dao.UserInfoBothDao;
import com.zzy.brd.entity.SysInfo;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.UserInfoBoth;
import com.zzy.brd.util.QRcode.QRcodeUtils;
import com.zzy.brd.util.file.FileUtil;

/**
 * 用户信息-会员
 * @author lzh 2016-9-26
 *
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserInfoBothService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(UserInfoBothService.class);
	
	@Autowired
	private UserInfoBothDao userInfoBothDao;
	
	@Autowired
	private SysInfoService sysInfoService;
	
	/**
	 * @param 修改信息
	 * @return
	 */
	public boolean editUserInfoBoth(UserInfoBoth userInfoBoth) {
		return userInfoBothDao.save(userInfoBoth) == null ? false : true;
	}
	
	/**
	 * 生成新的二维码图片 删除旧的二维码图片
	 * 
	 * */
	public void createQrcodeImg(User user ){
		UserInfoBoth userInfoBoth = user.getUserInfoBoth();
		String recommended ="";
		if(userInfoBoth!=null){		
			recommended = user.getUserInfoBoth().getRecommendCode();
		}
		if(userInfoBoth.getQrCode()!=null){
			String oldQr = userInfoBoth.getQrCode();
			int oldindex = oldQr.indexOf("filePath=");
			if (oldindex > -1) {
				oldQr=StringUtils.substringAfter(oldQr, "filePath=");
			}
			oldQr = oldQr.replace("\\", "/");
			try{
				FileUtil.deleteImg(oldQr);
			}catch(Exception e){
				System.out.println("删除旧二维码图片失败:"+e.getMessage());
			}
		}
		String filePath ="";
		String prodir = ConfigSetting.proPathByType(PathType.WEIXINQRCODE);
		String filename = FileUtil.hashPath(prodir, "weixinQrcode"+userInfoBoth.getId()+
				".png", userInfoBoth.getId()+"");
		File destFile = new File(filename);
		SysInfo sysinfo  =sysInfoService.findSysInfoById(1L);
		String qrmidurl = sysinfo.getApprenticeQrCodeUrl();
		int index = qrmidurl.indexOf("filePath=");
		if (index > -1) {
			qrmidurl=StringUtils.substringAfter(qrmidurl, "filePath=");
		}
		qrmidurl = qrmidurl.replace("\\", "/");
		String logoname =ConfigSetting.Pro_Dir+qrmidurl;
		File logoFile = new File(logoname);
		String qrCodeUrl = "http://"+ConfigSetting.File_Server_Ip+"/weixin/sharefriend/register/"+recommended;
		try{
			QRcodeUtils.createQRcodeImageForUser(qrCodeUrl, 1600, 1600, logoFile, destFile);
		}catch(Exception e){
			System.out.println("生成二维码错误:"+e.getMessage());
		}
		filePath = destFile.getAbsolutePath();
		filePath = filePath.substring(ConfigSetting.Pro_Dir.length());
		filePath = FileUtil.moveFileToPro(filePath);
		userInfoBoth.setQrCode(filePath);
		userInfoBothDao.save(userInfoBoth);
	}
	
}
