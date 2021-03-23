/*******************************************************************************
 * Copyright (c) 2005, 2014 zzy.cn
 *
 * 
 *******************************************************************************/
package com.zzy.brd.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.mapper.BeanMapper;
import org.springside.modules.utils.Reflections;

import com.google.common.collect.Lists;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.dao.SmsAuthcodeDao;
import com.zzy.brd.entity.SmsAuthcode;
import com.zzy.brd.enums.SmsAuthcodeSource;
import com.zzy.brd.util.date.DateUtil;
import com.zzy.brd.util.persistence.DynamicSpecifications;
import com.zzy.brd.util.persistence.SearchFilter;
import com.zzy.brd.util.sms.SMSUtil;



/**
 * @author huangjinbing
 * 2015年10月10日
 */
public class BaseService {
	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	/*@Autowired
	private SmsAuthcodeDao smsAuthcodeDao;*/
	
	static {
		DozerBeanMapper mapper = (DozerBeanMapper) Reflections.getFieldValue(
				new BeanMapper(), "dozer");
		mapper.setMappingFiles(Lists.newArrayList("globalConfig.xml"));
	}
	
	/**
	 * 发送短信校验码
	 * 
	 * @param teleno
	 *            手机号码
	 * 
	 * @return boolean 是否成功
	 */
	/*public boolean sendAuthcode(String teleno, SmsAuthcodeSource source) {
		// 获取校验码
		String smscode = SMSUtil.getSmscode();
		// 组织短信内容
		String content = "欢迎使用帮人贷，您的手机验证码是：" + smscode + "。";
		// 查询数据库是否存在该手机号码
		SmsAuthcode smsAuthcode = smsAuthcodeDao.findByTelenoAndSource(teleno,
				(short) source.ordinal());
		if (null == smsAuthcode) {// 不存在则新增
			smsAuthcode = new SmsAuthcode();
			smsAuthcode.setAuthcode(smscode);
			smsAuthcode.setSendDate(new Date(System.currentTimeMillis()));
			smsAuthcode.setTeleno(teleno);
			smsAuthcode.setSource((short) source.ordinal());
			smsAuthcodeDao.save(smsAuthcode);
		} else {// 存在则修改
			smsAuthcode.setAuthcode(smscode);
			smsAuthcode.setSendDate(new Date(System.currentTimeMillis()));
			smsAuthcodeDao.save(smsAuthcode);
		}
		// 调用短信中间件发送
		try {
			SMSUtil.sendMessage(smsAuthcode.getId(), smsAuthcode.getTeleno(),
					content);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送短信验证码失败:\n" + e.getMessage());
		}
		return true;
	}*/
	
	/**
	 * 验证短信校验码
	 * 
	 * @param teleno
	 *            注册的用户信息
	 * @param smsauthcode
	 *            输入的校验码
	 * @return 返回类型: 0成功 1-数据库中不存在 2-输入的校验码错误 3-输入的校验码已过期
	 */
	/*public int validateSmsAuthcode(String teleno, String smsauthcode,
			SmsAuthcodeSource source) {
		// 校验手机短信校验码
		SmsAuthcode smsAuthcode = smsAuthcodeDao.findByTelenoAndSource(teleno,
				(short) source.ordinal());
		if (null == smsAuthcode) {// 数据库不存在则说明没有点击发送短信校验码
			return 1;
		} else {
			if (!smsauthcode.equals(smsAuthcode.getAuthcode())) {// 校验码不匹配
				return 2;
			}
			if (System.currentTimeMillis()
					- smsAuthcode.getSendDate().getTime() > ConfigSetting.SMS_AUTH_CODE_TIMEOUT) {// 超时失效
				return 3;
			}
		}
		// 校验成功后则吧发送时间设置到超时前
		Calendar cal = Calendar.getInstance();
		cal.setTime(smsAuthcode.getSendDate());
		cal.add(Calendar.MINUTE, -ConfigSetting.SMS_AUTH_CODE_TIMEOUT);
		smsAuthcode.setSendDate(cal.getTime());
		smsAuthcodeDao.save(smsAuthcode);
		return 0;
	}*/

	/**
	 * 验证短信校验码(没修改发送时间 即用一次就失效)
	 * 
	 * @param teleno
	 *            注册的用户信息
	 * @param smsauthcode
	 *            输入的校验码
	 * @return 返回类型: 0成功 1-数据库中不存在 2-输入的校验码错误 3-输入的校验码已过期
	 */
	/*public int validateSmsAuthcode2(String teleno, String smsauthcode,
			SmsAuthcodeSource source) {
		// 校验手机短信校验码
		SmsAuthcode smsAuthcode = smsAuthcodeDao.findByTelenoAndSource(teleno,
				(short) source.ordinal());
		if (null == smsAuthcode) {// 数据库不存在则说明没有点击发送短信校验码
			return 1;
		} else {
			if (!smsauthcode.equals(smsAuthcode.getAuthcode())) {// 校验码不匹配
				return 2;
			}
			if (System.currentTimeMillis()
					- smsAuthcode.getSendDate().getTime() > ConfigSetting.SMS_AUTH_CODE_TIMEOUT) {// 超时失效
				return 3;
			}
		}
		// 校验成功后则吧发送时间设置到超时前
		return 0;
	}*/
	
	/**
	 * 创建分页请求.
	 * 
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页大小
	 * @param sortType
	 *            排序字段 默认：id 支持按多字段排序 "id:asc,name:desc" "id,name:desc"
	 * @param asc
	 *            是否升序
	 * @return PageRequest
	 */
	protected PageRequest createPageRequest(int pageNumber, int pageSize,
			String sortType, boolean asc) {
		Sort sort = createSort(sortType, asc);
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	/**
	 * 
	 * @param sortType
	 *            排序字段 默认：id 支持按多字段排序 "id:asc,name:desc" "id,name:desc"
	 * @param asc
	 *            是否升序
	 * @return
	 */
	protected Sort createSort(String sortType, boolean asc) {
		Sort sort = null;
		Direction defaultDirection = asc ? Direction.ASC : Direction.DESC;

		// 设置默认值
		if (StringUtils.isBlank(sortType)) {
			sortType = "id";
		}

		List<Sort.Order> orders = Lists.newArrayList();
		Direction otherDirection;
		for (String sortStr : sortType.split(",")) {
			String sortAndDirection[] = sortStr.split(":");
			if (sortAndDirection.length < 1) {
				continue;
			}
			if (sortAndDirection.length == 2) {
				otherDirection = sortAndDirection[1].equalsIgnoreCase("asc") ? Direction.ASC
						: Direction.DESC;
			} else {
				otherDirection = defaultDirection;
			}
			orders.add(new Sort.Order(otherDirection, sortAndDirection[0]));
		}
		sort = new Sort(orders);
		return sort;
	}
	/**
	 * 创建动态查询条件组合.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Specification<?> createSpecification(
			Map<String, Object> searchParams, Class entityClass) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification spec = DynamicSpecifications.bySearchFilter(
				filters.values(), entityClass);
		return spec;
	}
}
