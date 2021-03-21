package com.zzy.brd.dao;

import com.zzy.brd.entity.WeixinUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeixinUserDao extends BaseDao<WeixinUser>{
    @Query("select w from WeixinUser w where w.openid =?1 ")
    public List<WeixinUser> findWeixinUserByOpenId(String openId);

    @Query("select w from WeixinUser w where w.nickname like CONCAT(?1, '%')")
    public Page<WeixinUser> findWeixinUserByNickname(String name, Pageable page);

    @Query("select w from WeixinUser w where w.sceneids like CONCAT('%\"', ?1 ,'\"%') ")
    public Page<WeixinUser> findWeixinUserByScene(long sceneId, Pageable page);

    @Query("select w from WeixinUser w where w.sceneids like CONCAT('%\"', ?1 ,'\"%') and w.subscribe=?2 ")
    public Page<WeixinUser> findWeixinUserByScene(long sceneId, WeixinUser.SubscribeType subscribeType, Pageable page);
}
