package cn.meteor.beyondclouds.modules.mail.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@org.springframework.boot.test.context.SpringBootTest
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class IMailServiceTest {
    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.mail.service.IMailService mailService;

    @org.junit.Test
    public void sendSimpleMail() {
        cn.meteor.beyondclouds.modules.mail.dto.EmailDTO email = new cn.meteor.beyondclouds.modules.mail.dto.EmailDTO();
        email.setFrom("13546386889@163.com");
        email.setSubject("测试");
        email.setText("hello");
        email.setTo(new java.lang.String[]{ "1379681506@qq.com" });
        mailService.sendSimpleMail(email);
    }
}