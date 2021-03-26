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
	 * 注册会员
	 */
	public boolean register(User user,User parentUser){
		// 加密密码
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());

		BigDecimal bonus = null;
		boolean is1 =false;
		return true;
	}

	/**
	 * 添加员工
	 * 
	 * @param @param user
	 * @param @return
	 * @return int
	 */

	public int addUser(User user) {
		// 查看手机是否存在

		boolean isExist = userDao.findByMobileno(user.getMobileno()) == null ? false
				: true;
		if (isExist) {// 手机号已被注册
			return Constant.REGISTER_TYPE_MOBILE_EXIST;
		}
		isExist = userDao.findByUsername(user.getUsername()) == null ? false
				: true;

		if (isExist) {// 用户名已经注册
			return Constant.REGISTER_TYPE_USERNAME_EXIST;
		}

		if (isExist) {// 用户编号存在
			return Constant.REGISTER_USERNO_EXIST;
		}
		// 加密密码
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());

		return Constant.REGISTER_SUCC;
	}
	/**
	 * 重置密码
	 * @param user
	 * @return
	 */
	public boolean resetPassword(User user){
		// 加密密码
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());
		
		return userDao.save(user) == null?false:true;
}



	/**
	 * 根据userid查找用户
	 * 
	 * @param userid
	 * @return
	 */
	public User findUserById(long userid) {
		return userDao.findOne(userid);
	}
	
	/**
	 * 根据手机号查找用户
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
	 * 插入或修改个人图像
	 * 
	 * @param @param headimgurl
	 * @param @param user
	 * @param @return
	 * @return boolean
	 */
	public String applyHeadImage(String headimgurl, User user) {
		// 移动图片到正式路径
		int index = headimgurl.indexOf("filePath=");
		if (index > -1) {
			String tempPath = StringUtils.substringAfter(headimgurl,
					"filePath=");
			headimgurl = FileUtil.moveToProPathByTempPath(tempPath);
		}
		headimgurl = headimgurl.replace("\\", "/");
		// 新增时更新user表
		user.setHeadimgurl(headimgurl);
		boolean result = userDao.save(user) == null ? false : true;
		if (result) {
			return ConfigSetting.appfileProUrlByFilePath(headimgurl);
		} else {
			return "";
		}
	}

	/**
	 * 检查图片
	 * 
	 * @param headimgurl
	 * @return
	 */
	public boolean checkPicUrl(String headimgurl) {
		String[] strs = headimgurl.split(";");
		if (strs.length != 2) {
			// 图片错误
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
	 * 修改user
	 * @param user
	 * @return
	 */
	public boolean editUser(User user){
		return userDao.save(user)==null?false:true;
	}
	
	/**
	 * 根据角色查询用户
	 * 
	 * @param role
	 * @return
	 */
	public List<User> findByRole(Role role) {
		return userDao.findByRole(role);
	}
	/**
	 *根据 id 获取用户
	 *@param userId
	 * @return
	 **/
	public User findById(long userId){
		return userDao.findOne(userId);
	}

	
	/**
	 * 获取会员列表
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
	 * 查找未删除的user
	 * @param
	 * @return
	 */
	public List<User> findUsersNoDel(State state) {
		return userDao.findUsersNoDel(state);
	}

	
	/**
	 * 修改会员状态
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateWithState(long id, State state) {
		User user = userDao.findOne(id);
		if (user == null
				|| (state != State.DEL && user.getState() == State.DEL)) { // 不存在
			return false;
		}
		user.setState(state);
		userDao.save(user);
		return true;
	}
	
	/**
	 *后台注册会员 
	 * 
	 **/
	public boolean registerUser(User user,User parentUser,String expands,String address){
		// 加密密码
		SHA1Encrypt encrypt = SHA1Encrypt.getInstance();
		PasswordInfo pwdInfo = encrypt.encryptPassword(user.getPassword());
		user.setSalt(pwdInfo.getSalt());
		user.setPassword(pwdInfo.getPassword());

		return true;
	}
	/**
	 * 查找搜索启用的员工、会员
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
	 *查询出所有的会员 
	 **/
	public List<User> findStatisticsUser(){
		return userDao.findStatisticsUser();
	}

	
	/**通过用户类型找到会员列表
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
     * 判断字符串是否包含某个字符串
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
