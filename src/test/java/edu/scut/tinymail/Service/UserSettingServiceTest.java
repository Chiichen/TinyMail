package edu.scut.tinymail.Service;

import edu.scut.tinymail.domain.entity.Usersetting;
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
        usersettingService.addUserSetting(new Usersetting("chi", "smtp.qq.com", "2531693734", "ckj20030603", 0));
        System.out.println(usersettingService.getByName("chi").toString());
        System.out.println(usersettingService.addUserSetting(new Usersetting("chi", "smtp.qq.com", "2531693734", "ckj20030603", 0)).toString());
        System.out.println(usersettingService.setUserSetting(new Usersetting("chi", "smtp.qq.com", "2531693734", "ckj20030603", 0)).toString());
        System.out.println(usersettingService.setUserSetting(new Usersetting("chi", "smtp.qq.com", "2531693734", "ckj2003060", 0)).toString());

    }
}
