package com.zzy.brd.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzy.brd.algorithm.encrypt.shiro.PasswordInfo;
import com.zzy.brd.algorithm.encrypt.shiro.SHA1Encrypt;
import com.zzy.brd.constant.ConfigSetting;
import com.zzy.brd.constant.Constant;
import com.zzy.brd.dao.UserDao;
//import com.zzy.brd.dao.UserInfoBothDao;
//import com.zzy.brd.entity.Activity.ActivitySet;
import com.zzy.brd.entity.Role;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
//import com.zzy.brd.entity.UserInfoBoth;
//import com.zzy.brd.entity.UserInfoSeller;
//import com.zzy.brd.mobile.web.dto.rep.apprentice.RepMyApprenticeDTO;
import com.zzy.brd.shiro.session.SessionService;
import com.zzy.brd.util.file.FileUtil;
import com.zzy.brd.util.string.StringUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class UserService extends BaseService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private SessionService sessionService;
	
	public User getUser(String username) {
		return userDao.findByUsername(username);
	}
	public List<User> getUsers(String username){
		return userDao.findByUsername1(username);
	}
	/**
	 * ????????????
	 */
	public boolean register(User user,User parentUser){
		// ????????????
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());

		BigDecimal bonus = null;
		boolean is1 =false;
		return true;
	}

	/**
	 * ????????????
	 * 
	 * @param @param user
	 * @param @return
	 * @return int
	 */

	public int addUser(User user) {
		// ????????????????????????

		boolean isExist = userDao.findByMobileno(user.getMobileno()) == null ? false
				: true;
		if (isExist) {// ?????????????????????
			return Constant.REGISTER_TYPE_MOBILE_EXIST;
		}
		isExist = userDao.findByUsername(user.getUsername()) == null ? false
				: true;

		if (isExist) {// ?????????????????????
			return Constant.REGISTER_TYPE_USERNAME_EXIST;
		}

		if (isExist) {// ??????????????????
			return Constant.REGISTER_USERNO_EXIST;
		}
		// ????????????
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());

		return Constant.REGISTER_SUCC;
	}
	/**
	 * ????????????
	 * @param user
	 * @return
	 */
	public boolean resetPassword(User user){
		// ????????????
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());
		
		return userDao.save(user) == null?false:true;
}



	/**
	 * ??????userid????????????
	 * 
	 * @param userid
	 * @return
	 */
	public User findUserById(long userid) {
		return userDao.findOne(userid);
	}
	
	/**
	 * ???????????????????????????
	 * 
	 * @param mobileno
	 * @return
	 */
	public User findByMobileno(String mobileno) {
		return userDao.findByMobileno(mobileno);
	}
	public List<User> findByMobileno1(String mobileno){
		return userDao.findByMobileno1(mobileno);
	}
	
	/***
	 *  
	 **/
	public List<User> findByMobileno2(String mobileno,List<UserType> userTypeList ){
		return userDao.findByMobileno2(mobileno, userTypeList);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @param @param headimgurl
	 * @param @param user
	 * @param @return
	 * @return boolean
	 */
	public String applyHeadImage(String headimgurl, User user) {
		// ???????????????????????????
		int index = headimgurl.indexOf("filePath=");
		if (index > -1) {
			String tempPath = StringUtils.substringAfter(headimgurl,
					"filePath=");
			headimgurl = FileUtil.moveToProPathByTempPath(tempPath);
		}
		headimgurl = headimgurl.replace("\\", "/");
		// ???????????????user???
		user.setHeadimgurl(headimgurl);
		boolean result = userDao.save(user) == null ? false : true;
		if (result) {
			return ConfigSetting.appfileProUrlByFilePath(headimgurl);
		} else {
			return "";
		}
	}

	/**
	 * ????????????
	 * 
	 * @param headimgurl
	 * @return
	 */
	public boolean checkPicUrl(String headimgurl) {
		String[] strs = headimgurl.split(";");
		if (strs.length != 2) {
			// ????????????
			return false;
		}
		File pic1File = new File(ConfigSetting.Head_Person_Pic_Dir + strs[0]);
		File thumbnail1File = new File(ConfigSetting.Head_Person_Thumbnail_Dir
				+ strs[1]);
		boolean isPic1 = pic1File.exists();
		boolean isThumbnail1 = thumbnail1File.exists();

		if (!isPic1 || !isThumbnail1) {
			if (!isPic1) {
				pic1File.delete();
			}
			if (!isThumbnail1) {
				thumbnail1File.delete();
			}
		}
		if (!isPic1 || !isThumbnail1) {
			return false;
		}
		return true;
	}

	/**
	 * ??????user
	 * @param user
	 * @return
	 */
	public boolean editUser(User user){
		return userDao.save(user)==null?false:true;
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @param role
	 * @return
	 */
	public List<User> findByRole(Role role) {
		return userDao.findByRole(role);
	}
	/**
	 *?????? id ????????????
	 *@param userId
	 * @return
	 **/
	public User findById(long userId){
		return userDao.findOne(userId);
	}

	
	/**
	 * ??????????????????
	 * 
	 * @param @param searchParams
	 * @param @param pageNumber
	 * @param @param pageSize
	 * @param @param sortName
	 * @param @param sortType
	 * @param @return
	 * @return Page<User>
	 */
	@Transactional(readOnly = true)
	public Page<User> listUsers(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortName, String sortType) {
		PageRequest pageRequest;

		if(!StringUtils.isBlank(sortName) && !StringUtils.isBlank(sortType)){
			String sort = sortName+":"+sortType;
			pageRequest = createPageRequest(pageNumber, pageSize, sort, false);
		}else{
			pageRequest = createPageRequest(pageNumber,pageSize,"createTime:desc",false);
		}
		@SuppressWarnings("unchecked")
		Specification<User> spec = (Specification<User>) createSpecification(searchParams, User.class);
		Page<User> result = userDao.findAll(spec, pageRequest);
		return result;
	}

	/**
	 * ??????????????????user
	 * @param
	 * @return
	 */
	public List<User> findUsersNoDel(State state) {
		return userDao.findUsersNoDel(state);
	}

	
	/**
	 * ??????????????????
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateWithState(long id, State state) {
		User user = userDao.findOne(id);
		if (user == null
				|| (state != State.DEL && user.getState() == State.DEL)) { // ?????????
			return false;
		}
		user.setState(state);
		userDao.save(user);
		return true;
	}
	
	/**
	 *?????????????????? 
	 * 
	 **/
	public boolean registerUser(User user,User parentUser,String expands,String address){
		// ????????????
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());

		return true;
	}
	/**
	 * ????????????????????????????????????
	 * 
	 * @param mobileno
	 * @param realname
	 * @param username
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<User> listAllUsers(String username, String realname,
			String mobileno) {
		if (!StringUtils.isBlank(realname)) {
			realname = StringUtil.escapeLikeString(realname);
		}
		if (!StringUtils.isBlank(mobileno)) {
			mobileno = StringUtil.escapeLikeString(mobileno);
		}
		if (!StringUtils.isBlank(username)) {
			username = StringUtil.escapeLikeString(username);
		}
		List<User> result = userDao.listAllUsers(username, realname, mobileno);
		return result;
	}
	
	/**
	 *???????????????????????? 
	 **/
	public List<User> findStatisticsUser(){
		return userDao.findStatisticsUser();
	}

	
	/**????????????????????????????????????
	 * @param
	 *
	 */
	public List<User> findUserByUsertype(UserType userType){
		return userDao.findUserByUsertype(userType);
	}

	 public User findByMobileAndUserType(String mobile,List<UserType> typeList){
		 return userDao.findByMobileAndUerType(mobile, typeList);
	 }
	 
    /**
     * ??????????????????????????????????????????
     * @param str1
     * @param str2
     * @return
     */
    public boolean isExit(String str1,String str2){
    	if(str1.indexOf(str2)!=-1){
    		return true;
    	}else{
    		return false;
    	}
    }

}
