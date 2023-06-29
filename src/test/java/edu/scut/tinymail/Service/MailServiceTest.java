package edu.scut.tinymail.Service;

import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.service.MailService;
import edu.scut.tinymail.service.UsersettingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Autowired
    UsersettingService usersettingService;

    @Test
    public void context() throws IOException {
        Mail mail = new Mail("测试", "这是一个测试邮件", "2531693734@qq.com", "2383690608@qq.com");
        System.out.println(usersettingService.getSetting("chi", "2531693734@qq.com"));
        System.out.println(mailService.send("chi", "2531693734@qq.com", mail));
    }
}
