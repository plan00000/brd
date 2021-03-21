package com.zzy.brd.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.dao.WeixinPostDao;
import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.entity.WeixinPost.NoticeType;


/***
 * 微信推送service
 * 
 * @author csy 2016-11-22
 *
 */
/*@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class WeixinPostService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(WeixinPostService.class);
	@Autowired
	private WeixinPostDao weixinPostDao;

	public WeixinPost findWeixinPostById(long id) {
		return weixinPostDao.findOne(id);
	}
	*//**
	 * 根据类型获得微信通知
	 * @param noticeType
	 * @return
	 *//*
	public WeixinPost findWeixinPostByType(NoticeType noticeType) {
		return weixinPostDao.findWeixinPostByType(noticeType);
	}
	
	public boolean editWeixinPost(WeixinPost weixinPost){
		return weixinPostDao.save(weixinPost)==null?false:true;
	}
	
	public List<WeixinPost> allWeixinPost(){
		return weixinPostDao.allWeixinPost();
	}
	
}*/
