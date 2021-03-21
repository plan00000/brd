package com.zzy.brd.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.entity.FriendshipLink;

/**
 * @author:xpk
 *    2016年12月2日-下午4:40:34
 **/
public interface FriendshipLinkDao extends BaseDao<FriendshipLink> {
	
	@Query("select f from FriendshipLink f where f.title=?1 and f.status = 0")
	FriendshipLink findByTitle(String title);
	
	@Query("select f from FriendshipLink f where f.status = 0")
	List<FriendshipLink> findForfont();
	
}
