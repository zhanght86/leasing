

 
import java.util.Properties; 
import javax.mail.Address; 
import javax.mail.Message; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 
 
public class TestEmail { 
    public void SendEmailTest() { 
        Properties props = new Properties(); 
        props.put("mail.smtp.host", "smtp.qq.com"); 
        props.put("mail.smtp.auth", "true"); // 允许smtp校验 
        Session sendMailSession = Session.getInstance(props, null); 
 
        try { 
            Transport transport = sendMailSession.getTransport("smtp"); 
            // 连接你的QQ，注意用户名和密码必须填写正确，否则权限得不到 
            transport.connect("smtp.qq.com", "973693521@qq.com", "lanren8888pml"); 
            Message newMessage = new MimeMessage(sendMailSession); 
 
            // 设置mail主题 
            String mail_subject = "更改邮件发送人测试"; 
            newMessage.setSubject(mail_subject); 
 
            // 设置发信人地址 
            String strFrom = "973693521@qq.com"; 
            strFrom = new String(strFrom.getBytes(), "iso-8859-1"); 
            newMessage.setFrom(new InternetAddress(strFrom)); 
            // Address addressFrom[] = { new 
            // InternetAddress("617334015@qq.com"),new 
            // InternetAddress("goodnight0002@163.com") }; 
            // 改变发件人地址 
            // newMessage.addFrom(addressFrom); 
            // 设置收件人地址 
            Address addressTo[] = { new InternetAddress("624817356@qq.com") }; 
            newMessage.setRecipients(Message.RecipientType.TO, addressTo); 
 
            // 设置mail正文 
            newMessage.setSentDate(new java.util.Date()); 
            String mail_text = "java实现邮件发送！"; 
            newMessage.setText(mail_text); 
 
            newMessage.saveChanges(); // 保存发送信息 
            transport.sendMessage(newMessage, newMessage 
                    .getRecipients(Message.RecipientType.TO)); // 发送邮件 
 
            transport.close(); 
            // Transport.send(newMessage); 
            System.out.println("发送成功!"); 
        } catch (Exception e) { 
            System.out.println("发送失败！"); 
            System.out.println(e); 
        } 
 
    } 
 
    public static void main(String args[]) throws Exception { 
        TestEmail SEmail = new TestEmail(); 
        SEmail.SendEmailTest(); 
    } 
} 