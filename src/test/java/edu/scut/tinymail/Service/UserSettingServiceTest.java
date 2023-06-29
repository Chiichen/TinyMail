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
        System.out.println(usersettingService.getImapSetting("chi", "2531693734@qq.com"));
        System.out.println(usersettingService.getSmtpSetting("chi", "2531693734@qq.com"));
        System.out.println(usersettingService.addUserSetting(new Usersetting("chi", "smtp.qq.com", "2531693734@qq.com", "mmvauqlcxnxbebdf", Usersetting.SMTP)));
        System.out.println(usersettingService.addUserSetting(new Usersetting("chii", "smtp.qq.com", "2531693734@qq.com", "mmvauqlcxnxbebdf", Usersetting.SMTP)));
        System.out.println(usersettingService.getSmtpSetting("qihua", "asdf"));
        System.out.println(usersettingService.setUserSetting(new Usersetting("chi", "smtp.qq.com", "2531693734@qq.com", "mmvauqlcxnxbebdf", Usersetting.SMTP)));
        System.out.println(usersettingService.setUserSetting(new Usersetting("chii", "smtp.qq.com", "2531693734@qq.com", "mmvauqlcxnxbebdf", Usersetting.SMTP)));
    }
}
