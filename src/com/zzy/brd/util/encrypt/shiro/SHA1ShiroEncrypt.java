package com.zzy.brd.util.encrypt.shiro;

import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

/***
 * sha-1加密 用于shiro密码加密
 * 
 * @author wwy
 *
 */
public class SHA1ShiroEncrypt  {
	// 算法名称
	private static String algorithName = "SHA-1";
	// hash迭代次数
	private static int hashIterations = 1024;
	// salt的长度
	private static int saltLength = 8;

	private static volatile SHA1ShiroEncrypt instance;

	private SHA1ShiroEncrypt() {
	}

	public String getHashAlgorithName() {
		return algorithName;
	}

	public int getHashIterations() {
		return hashIterations;
	}

	public PasswordInfo encryptPassword(String password) {
		PasswordInfo info = new PasswordInfo();
		byte[] salt = Digests.generateSalt(saltLength);
		info.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(password.getBytes(), salt,
				hashIterations);
		info.setPassword(Encodes.encodeHex(hashPassword));
		return info;
	}
	public String encryptPasswordBySalt(String password, String salt){
		byte[] bSalt =Encodes.decodeHex(salt);
		byte[] hashPassword = Digests.sha1(password.getBytes(), bSalt,
				hashIterations);
		return Encodes.encodeHex(hashPassword);
	}

	public static SHA1ShiroEncrypt getInstance() {
		if (instance == null) {
			synchronized (SHA1ShiroEncrypt.class) {
				if (instance == null) {
					instance = new SHA1ShiroEncrypt();
				}
			}
		}
		return instance;
	}

}
