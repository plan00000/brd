package com.zzy.brd.shiro.realm;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springside.modules.utils.Encodes;

import com.zzy.brd.constant.SessionKey;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.UserType;
import com.zzy.brd.service.UserService;
import com.zzy.brd.shiro.principal.ShiroUser;
import com.zzy.brd.shiro.token.CaptchaUsernamePasswordToken;
import com.zzy.brd.util.encrypt.shiro.SHA1ShiroEncrypt;

/**
 * pc及微信手机站realm
 * @author lzh 2016/9/13
 *
 */
public class ShiroUserDbRealm extends AuthorizingRealm{
	private UserService userService;
	
	/**
	 * 认证回调函数,登录时调用. 抛出异常，返回null，密码验证错误都会返回到loginUrl，可通过session来传递信息
	 * 如果成功的话，会返回到successUrl
	 */
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		if (authenticationToken instanceof CaptchaUsernamePasswordToken) {
			CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authenticationToken;
			User user = null;
			List<User> users = userService.findByMobileno1(token.getUsername());
			for(User userone :users){
				if(userone.getUserType().ordinal() ==0 || userone.getUserType().ordinal() ==1|| userone.getUserType().ordinal() ==2 ||userone.getUserType().ordinal() ==4 || userone.getUserType().ordinal()==5 ){
					user = userone;
				}
			}
			/*user = userService.findByMobileno(token.getUsername());*/
			Session session = SecurityUtils.getSubject().getSession(true);
			if(user==null){
				session.setAttribute(SessionKey.SESSION_USER_AUTH_ERROR, "用户名或密码错误");
				throw new UnknownAccountException("找不到对应的用户");
			}
			if (user.getState() == User.State.OFF){
				session.setAttribute(SessionKey.SESSION_USER_AUTH_ERROR, "该帐号已被禁用");
				throw new LockedAccountException("用户已被禁用");
			}
			if (user.getState() == User.State.DEL){
				session.setAttribute(SessionKey.SESSION_USER_AUTH_ERROR, "用户已被删除");
				throw new DisabledAccountException("用户已被删除");
			}
			//判断是否有权限登录到前台
			if(user.getRole() !=null){
				if(!user.getRole().getPermissionList().contains("LOGIN_MANAGER_FRONT")){
					session.setAttribute(SessionKey.SESSION_USER_LOGIN_AUTHORITY, "该账号无权限登录");
					return null;
				}
			}
			
			byte[] salt = Encodes.decodeHex(user.getSalt());
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
					new ShiroUser(user.getId(), user.getUserType(),user.getRealname(), null), // 用户名
					user.getPassword(), // 密码
					ByteSource.Util.bytes(salt),//
					getName() // realm name
			);
			return authenticationInfo;
		}
		
		return null;
	}
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
	
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		SHA1ShiroEncrypt encrypt = SHA1ShiroEncrypt.getInstance();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				encrypt.getHashAlgorithName());
		matcher.setHashIterations(encrypt.getHashIterations());
		setCredentialsMatcher(matcher);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

}
