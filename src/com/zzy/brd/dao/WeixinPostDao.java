package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.WeixinPost;
import com.zzy.brd.entity.WeixinPost.NoticeType;

/**
 * @author:csy
 *    2016年11月22日-上午11:21:54
 **/
public interface WeixinPostDao extends BaseDao<WeixinPost> {
	
	@Query("select w from WeixinPost w where w.noticeType =?1 ")
	public WeixinPost findWeixinPostByType(NoticeType noticeType);
	
	@Query("select w from WeixinPost w  ")
	public List<WeixinPost> allWeixinPost();

}
