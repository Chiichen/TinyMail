package edu.scut.tinymail.Service;

import edu.scut.tinymail.service.UsersettingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSettingServiceTest {
    @Autowired
    UsersettingService usersettingService;

    @Test
    public void context() {
        System.out.println(usersettingService.getImapSetting("2531693734@qq.com"));
        System.out.println(usersettingService.getSmtpSetting("2531693734@qq.com"));

    }
}
