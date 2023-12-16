package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailUtils {
    private static String USER = ""; // 发件人称号，同邮箱地址
    private static String PASSWORD = ""; // 如果是qq邮箱可以使户端授权码，或者登录密码

    static {
        Properties pro = new Properties();

        try {
            InputStream rs = MailUtils.class.getClassLoader().getResourceAsStream("mail.properties");
            pro.load(rs);
            USER = pro.getProperty("user");
            PASSWORD = pro.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param to 收件人邮箱
     * @param text 邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title){
        //收件人邮箱地址 to，邮件正文内容 text 和邮件标题 title。该方法返回一个布尔值，表示邮件是否成功发送。
        try {
            final Properties props = new Properties();
            //创建一个Properties对象，用于配置邮件发送相关的属性，如SMTP服务器、认证等信息。
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");

            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);

            //创建一个Authenticator对象，用于进行SMTP身份验证，需要提供发件人的用户名和密码。
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);
            //InternetAddress 主要用于以下几个方面：
            //创建邮件地址：您可以使用 InternetAddress 类创建一个邮件地址对象，传入邮件地址的字符串形式或者传入邮件地址的部分信息，如姓名和电子邮件地址。
            //
            //解析邮件地址：InternetAddress 有方法可以解析邮件地址的不同部分，如姓名和电子邮件地址。
            //
            //表示邮件地址：您可以将 InternetAddress 对象设置为邮件消息的发件人或收件人，以确保邮件的接收者知道邮件是从哪里来的。
            //
            //验证邮件地址：InternetAddress 还可以帮助验证邮件地址的格式是否正确。


            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            System.out.println("发送成功");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("发送失败");
        return false;
    }

    public static void main(String[] args) throws Exception { // 做测试用
        MailUtils.sendMail("milniktwistr@hotmail.com","你好，这是一封测试邮件，无需回复。","测试邮件");

    }



}

