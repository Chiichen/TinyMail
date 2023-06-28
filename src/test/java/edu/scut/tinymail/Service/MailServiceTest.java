package edu.scut.tinymail.Service;

import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void context() throws IOException {
        Mail mail = new Mail("测试", "这是一个测试邮件", "2531693734@qq.com", "2383690608@qq.com");
        mailService.send("2531693734@qq.com", mail);
    }
}
