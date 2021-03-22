package com.zzy.brd.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.zzy.brd.dao.UserInfoEmployeeDao;
import com.zzy.brd.entity.User;
//import com.zzy.brd.entity.UserInfoEmployee;
import com.zzy.brd.util.ip.AddressUtils;
import com.zzy.brd.util.ip.IpUtils;

/**
 * 用户信息-公共
 * @author lzh 2016-9-25
 *
 */
/*@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserInfoEmployeeService extends BaseService{
	@Autowired
	private UserInfoEmployeeDao userInfoPublicDao;
	*//**
	 * 后台登录-更新登录信息
	 * @param user
	 * @param request
	 * @return
	 *//*
	public boolean updateLoginInfo(User user,HttpServletRequest request){
		if(user.getUserInfoEmployee()!=null){
			UserInfoEmployee userInfoPublic = user.getUserInfoEmployee();
			//userInfoPublic.setLastlogindate(userInfoPublic.getLogindate());
			userInfoPublic.setLastloginIp(userInfoPublic.getLoginIp());
			userInfoPublic.setLastloginCity(userInfoPublic.getLoginCity());
//			userInfoPublic.setLoginIp(IpUtils.getIpAddr(request));
			//userInfoPublic.setLogindate(new Date());
			user.setLogindate(new Date());
			user.setLastlogindate(user.getLogindate());
			
			
			AddressUtils addressUtils = new AddressUtils();
			String address = "";
//			try {
//				address = addressUtils.getAddresses(
//						"ip=" + IpUtils.getIpAddr(request), "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			userInfoPublic.setLoginCity(address);
			return (userInfoPublicDao.save(userInfoPublic)==null?false:true);
		}
		return true;
	}
}*/
