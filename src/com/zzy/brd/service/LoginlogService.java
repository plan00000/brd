package com.zzy.brd.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.LoginlogDao;
import com.zzy.brd.entity.Loginlog;
import com.zzy.brd.entity.User;
import com.zzy.brd.util.ip.AddressUtils;
import com.zzy.brd.util.ip.IpUtils;

/**
 * @author:xpk
 *    2016年10月19日-下午6:20:45
 **/
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class LoginlogService extends BaseService {
	
	@Autowired LoginlogDao loginlogDao;
	/**
	 * @param searchParams
	 * @param pageNumber
	 * @param sortType
	 * @param asc
	 **/	
	@SuppressWarnings("unchecked")
	public Page<Loginlog> listLoginlog(Map<String,Object> searchParams,int pageNumber){
		Pageable pageRequest = this.createPageRequest(pageNumber, Constant.PAGE_SIZE, "id:desc", false);
		Specification<Loginlog> spec =(Specification<Loginlog>) createSpecification(searchParams, Loginlog.class); 
		return loginlogDao.findAll(spec, pageRequest);
	}
	
	
	/***
	 *增加登录日志 
	 */
	public void addLoginlog(User user,HttpServletRequest request){
		Loginlog log = new Loginlog();
		log.setUser(user);
		int loginTimes =0;
		try{
			loginTimes = loginlogDao.findMaxLoginTimes(user);
		}catch(Exception e){
			
		}
		log.setLoginTimes(loginTimes+1);
		String ip = IpUtils.getIpAddr(request);
		log.setUserLoginIp(ip);
		AddressUtils addressUtils = new AddressUtils();
		String address = "";
		try {
			address = addressUtils.getAddresses(
					"ip=" + ip, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		log.setLoginAddress(address);
		log.setLoginDate(new Date());
		loginlogDao.save(log);
	}
	
	/**
	 * 查询指定之间段内的登录内容
	 * 
	 */
	public List<Loginlog> findByTimeAndUser(Date startTime,Date endTime,User user){
		return loginlogDao.findByTimeByUser(startTime, endTime, user);
	}
	
	
	
	
}
