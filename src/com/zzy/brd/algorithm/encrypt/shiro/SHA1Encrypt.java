package com.zzy.brd.algorithm.encrypt.shiro;

import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

/***
 * sha-1加密 用于shiro密码加密
 * 
 * @author wwy
 *
 */
public class SHA1Encrypt  {
	// 算法名称
	private static String algorithName = "SHA-1";
	// hash迭代次数
	private static int hashIterations = 1024;
	// salt的长度
	private static int saltLength = 8;

	private static volatile SHA1Encrypt instance;

	private SHA1Encrypt() {
	}

	public String getHashAlgorithName() {
		return algorithName;
	}

	public int getHashIterations() {
		return hashIterations;
	}

	/***
	 * 加密
	 * @param password
	 * @return
	 */
	public PasswordInfo encryptPassword(String password) {
		PasswordInfo info = new PasswordInfo();
		byte[] salt = Digests.generateSalt(saltLength);
		info.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(password.getBytes(), salt,
				hashIterations);
		info.setPassword(Encodes.encodeHex(hashPassword));
		return info;
	}

	/***
	 * 加密
	 * @param password 
	 * @param salt
	 * @return
	 */
	public String encryptPasswordBySalt(String password, String salt){
		byte[] bSalt = Encodes.decodeHex(salt);
		byte[] hashPassword = Digests.sha1(password.getBytes(), bSalt,
				hashIterations);
		return Encodes.encodeHex(hashPassword);
	}
	public static SHA1Encrypt getInstance() {
		if (instance == null) {
			synchronized (SHA1Encrypt.class) {
				if (instance == null) {
					instance = new SHA1Encrypt();
				}
			}
		}
		return instance;
	}
	public static void main(String[] args) {
		String password = "96e79218965eb72c92a549dd5a330112";
		String salt = "b72af000a68b7b37";
		System.out.println(SHA1Encrypt.getInstance().encryptPasswordBySalt(password, salt));
	}
}
