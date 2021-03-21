package com.zzy.brd.util.mail;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/***
 * 邮件发送工具类<br/>
 * 建议使用spring注入，维持单例
 * @author wwy
 *
 */
public class MailUtils {
	/** 发送邮件的用户的用户名 */
	private String userName;
	/** 发送邮件的用户的密码 */
	private String password;
	/** 发送邮件的用户的邮件服务器地址 */
	private String host;

	/***
	 * 发送邮件，功能较完全<br/>
	 * 说明：嵌入图片<img src=\"cid:aaa\"/>，其中cid:是固定的写法，而aaa是一个contentId。
	 * 
	 * @param subject
	 *            主题
	 * @param text
	 *            文本，html格式
	 * @param sendTo
	 *            发送到
	 * @param attachFile
	 *            附件
	 * @param inlineFile
	 *            内嵌文件
	 * @throws MessagingException
	 */
	public void sendMail(String subject, String text, String[] sendTo,
			Map<String, File> attachFile, Map<String, File> inlineFile)
			throws MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost(host);
		senderImpl.setDefaultEncoding("UTF-8");
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");
		// 设置收件人，寄件人
		messageHelper.setTo(sendTo);
		messageHelper.setFrom(userName);
		messageHelper.setSubject(subject);
		// true 表示启动HTML格式的邮件
		messageHelper.setText(text, true);
		// 设置附件
		Set<String> fileNames = attachFile.keySet();
		if (fileNames != null && fileNames.size() > 0) {
			for (String fileName : fileNames) {
				messageHelper.addAttachment(fileName, attachFile.get(fileName));
			}
		}
		// 设置内嵌文件
		Set<String> contentIds = inlineFile.keySet();
		if (contentIds != null && contentIds.size() > 0) {
			for (String contentId : contentIds) {
				messageHelper.addInline(contentId, inlineFile.get(contentId));
			}
		}
		// 根据自己的情况,设置username
		senderImpl.setUsername(userName);
		// 根据自己的情况, 设置password
		senderImpl.setPassword(password);
		Properties prop = new Properties();
		// 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println("邮件发送成功..");
	}

	/***
	 * 发送纯文本邮件
	 * 
	 * @param subject
	 * @param text
	 * @param sendTo
	 */
	public void sendTextMail(String subject, String text, String[] sendTo) {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(host);

		// 建立邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人 用数组发送多个邮件
		// String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
		// mailMessage.setTo(array);
		mailMessage.setTo(sendTo);
		mailMessage.setFrom(userName);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);

		senderImpl.setUsername(userName); // 根据自己的情况,设置username
		senderImpl.setPassword(password); // 根据自己的情况, 设置password

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println(" 邮件发送成功.. ");
	}

	/***
	 * 发送html格式的邮件
	 * 
	 * @param subject
	 * @param text
	 * @param sendTo
	 * @throws MessagingException
	 */
	public void sendHtmlMail(String subject, String text, String[] sendTo)
			throws MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost(host);
		senderImpl.setDefaultEncoding("UTF-8");
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);

		// 设置收件人，寄件人
		messageHelper.setTo(sendTo);
		messageHelper.setFrom(userName);
		messageHelper.setSubject(subject);
		// true 表示启动HTML格式的邮件
		messageHelper.setText(text, true);

		senderImpl.setUsername(userName); // 根据自己的情况,设置username
		senderImpl.setPassword(password); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println("邮件发送成功..");
	}

	/***
	 * 发送带附件的html邮件
	 * 
	 * @param subject
	 * @param text
	 * @param sendTo
	 * @param attachFile
	 * @throws MessagingException
	 */
	public void sendHtmlMailWithAttachFile(String subject, String text,
			String[] sendTo, Map<String, File> attachFile)
			throws MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost(host);
		senderImpl.setDefaultEncoding("UTF-8");
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");

		// 设置收件人，寄件人
		messageHelper.setTo(sendTo);
		messageHelper.setFrom(userName);
		messageHelper.setSubject(subject);
		// true 表示启动HTML格式的邮件
		messageHelper.setText(text, true);
		Set<String> fileNames = attachFile.keySet();
		for (String fileName : fileNames) {
			messageHelper.addAttachment(fileName, attachFile.get(fileName));
		}
		senderImpl.setUsername(userName); // 根据自己的情况,设置username
		senderImpl.setPassword(password); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println("邮件发送成功..");
	}

	public void sendHtmlMailWithInlineFile(String subject, String text,
			String[] sendTo, Map<String, File> inlineFile)
			throws MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost(host);
		senderImpl.setDefaultEncoding("UTF-8");
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");

		// 设置收件人，寄件人
		messageHelper.setTo(sendTo);
		messageHelper.setFrom(userName);
		messageHelper.setSubject(subject);
		// true 表示启动HTML格式的邮件
		messageHelper.setText(text, true);
		Set<String> contentIds = inlineFile.keySet();
		for (String contentId : contentIds) {
			messageHelper.addInline(contentId, inlineFile.get(contentId));
		}
		senderImpl.setUsername(userName); // 根据自己的情况,设置username
		senderImpl.setPassword(password); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		System.out.println("邮件发送成功..");
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
