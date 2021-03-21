package com.zzy.brd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.sd4324530.fastweixin.api.JsAPI;
import com.github.sd4324530.fastweixin.api.response.GetSignatureResponse;
import com.github.sd4324530.fastweixin.util.JsApiUtil;
import com.zzy.brd.dao.WeixinUserDao;
import com.zzy.brd.entity.WeixinUser;

import java.util.List;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class WeixinUserService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(WeixinUserService.class);
	@Autowired
	private WeixinUserDao weixinUserDao;
	@Autowired
	private JsAPI jsAPI;

	/**
	 * 分页查询用户
	 * @param sceneId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<WeixinUser> findUserByScene(long sceneId, int pageNumber, int pageSize, boolean asc) {
		PageRequest pageRequest = createPageRequest(pageNumber, pageSize,"subscribeTime", asc);
		return this.weixinUserDao.findWeixinUserByScene(sceneId, pageRequest);
	}

	public Page<WeixinUser> findUserByScene(long sceneId, WeixinUser.SubscribeType subscribeType, int pageNumber, int pageSize, boolean asc) {
		PageRequest pageRequest = createPageRequest(pageNumber, pageSize,"subscribeTime", asc);
		return this.weixinUserDao.findWeixinUserByScene(sceneId, subscribeType, pageRequest);
	}

	public Page<WeixinUser> findUserByNickname(String nickname, int pageNumber, int pageSize) {

		Pageable page = createPageRequest(pageNumber, pageSize, "id", true);

		return this.weixinUserDao.findWeixinUserByNickname(nickname, page);
	}

	/**
	 * 根据openid获取用户
	 * @param openId
	 * @return
	 */
	public WeixinUser findUserByOpenId(String openId) {
		List<WeixinUser> list = this.weixinUserDao.findWeixinUserByOpenId(openId);
		if (list.isEmpty()) {
			return null;
		}
		else return list.get(0);
	}

	/**
	 * 修改user
	 * @param weixinUser
	 * @return
	 */
	public boolean editUser(WeixinUser weixinUser){
		// 防止无效的微信用户写入数据库, 用于调试weixin 为空的bug
//		if (weixinUser.getNickname() == null) {
//			this.logger.error("无效的微信用户, openid: " + weixinUser.getOpenid());
//			throw new RuntimeException("无效的微信用户");
//		}
		return weixinUserDao.save(weixinUser)==null?false:true;
	}
	/**
	 * 根据id得到微信用户
	 * @param weixinUserId
	 * @return
	 */
	public WeixinUser getWeixinUserById(Long weixinUserId){
		return weixinUserDao.findOne(weixinUserId);
	}
	
	/**
	 * 获得jsSDK 所需要的签名参数等
	 * 
	 * */
	public GetSignatureResponse getJsParam(String url){
		return jsAPI.getSignature(url);
	}
	
}
