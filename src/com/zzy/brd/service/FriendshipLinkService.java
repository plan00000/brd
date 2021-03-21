package com.zzy.brd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.FriendshipLinkDao;
import com.zzy.brd.entity.FriendshipLink;

/**
 * @author:xpk
 *    2016年12月2日-下午4:39:22
 **/
@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
public class FriendshipLinkService extends BaseService {
	
	private @Autowired FriendshipLinkDao friendshipLinkDao;
	
	public FriendshipLink findById(long id){
		return friendshipLinkDao.findOne(id);
	}
	
	public boolean editFriendship(FriendshipLink friendship){
		return friendshipLinkDao.save(friendship) ==null ?false :true;
	}
	
	public FriendshipLink findByTitle(String title){
		return friendshipLinkDao.findByTitle(title);
	}
	
	public List<FriendshipLink> findForFont(){
		return friendshipLinkDao.findForfont();
	}
	
	@SuppressWarnings("unchecked")
	public Page<FriendshipLink> getFriendshipLinkList(int pageNumber){
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("EQ_status",FriendshipLink.Status.NORMAL);
		PageRequest pageRequest = this.createPageRequest(pageNumber, Constant.PAGE_SIZE, "id:asc", false);
		Specification<FriendshipLink> spec = (Specification<FriendshipLink>) this.createSpecification(searchParams, FriendshipLink.class);	
		return friendshipLinkDao.findAll(spec,pageRequest);
	}
	
	
	
}
