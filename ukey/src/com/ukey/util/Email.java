package com.ukey.util;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.util.DigestUtils;

public class Email
{
	public static String sendEmail(String email)
	{
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.163.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		// 使用JavaMail发送邮件的5个步骤
		// 1、创建session
		Session session = Session.getInstance(prop);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		// session.setDebug(true);
		// 创建验证码，MD5加密
		String identificationCode = UUID.randomUUID().toString();
		identificationCode = DigestUtils.md5DigestAsHex(identificationCode.getBytes());
		try
		{
			// 2、通过session得到transport对象
			Transport ts = session.getTransport();
			// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			ts.connect("smtp.163.com", "ukeyweb", "ukey000");
			// 4、创建邮件
			Message message = createSimpleMail(session, email, identificationCode);
			// 5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return identificationCode;
	}

	public static MimeMessage createSimpleMail(Session session, String email, String identificationCode) throws Exception
	{
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress("ukeyweb@163.com"));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		// 邮件的标题
		message.setSubject("ukey修改密码验证");
		// 邮件的文本内容
		String content = "<h5>亲爱的ukey用户:</h5>\r\n" + "			您好！\r\n"
				+ "			我们接受到了您的密码遗失的请求，<a href=\"%s\">请点击这个链接</a>，点击后即可修改您的密码，请您务必在同一个浏览器里操作，否则可能导致跳转不成功！<br/>\r\n"
				+ "			若遇到任何问题，请与我们联系。<br />\r\n" + "			联系电话：15281737599，我们将努力解决您的问题<br />";
		String href = String.format("http://localhost:8080/ukey/user/changePassword?identificationCode=%s",
				identificationCode);
		content = String.format(content, href);
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}
}