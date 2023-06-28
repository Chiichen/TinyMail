package edu.scut.tinymail.controller;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Userauth;
import edu.scut.tinymail.domain.entity.Usersetting;
import edu.scut.tinymail.service.UserauthService;
import edu.scut.tinymail.service.UsersettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "UserController", description = "用户数据接口")
public class UserController {

    @Autowired
    UserauthService userauthService;

    @Autowired
    UsersettingService usersettingService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserDetailsPasswordService userDetailsPasswordService;

    @Operation(summary = "注册接口")
    @PostMapping("/api/user/register")
    public ResponseResult<?> register(
            @Parameter(description = "用户信息，包括验证码和账号密码")
            Userauth userauth, HttpServletRequest request) {
        if (userauth.getVc().isBlank()) return new ResponseResult<>(401, "请输入验证码");

        else return userauthService.register(userauth, request);
    }

    @Operation(summary = "修改用户密码")
    @PostMapping("/api/user/updatePassword")
    public ResponseResult<?> updatePassword(String username, String newpassword) {
        return new ResponseResult<>(200, "成功修改密码",
                userDetailsPasswordService
                        .updatePassword(
                                userDetailsService
                                        .loadUserByUsername(username), newpassword)
        );
    }

    @Operation(summary = "给指定用户添加配置信息")
    @PostMapping("/api/user/addsetting")
    public ResponseResult<?> addEmailSettings(Usersetting usersetting) {
        if (userauthService.getByUsername(usersetting.getUsername()) == null)
            return new ResponseResult<>(403, "该用户不存在");
        return usersettingService.addUserSetting(usersetting);
    }

    @Operation(summary = "从用户名获取配置信息")
    @GetMapping("api/user/getsetting")
    public ResponseResult<?> getSettingByName(String username) {
        if (userauthService.getByUsername(username) == null) return new ResponseResult<>(403, "该用户不存在");
        return usersettingService.getByName(username);
    }

    @Operation(summary = "修改配置信息(修改某个邮箱配置项的账号密码)")
    @PostMapping("api/user/setsetting")
    public ResponseResult<?> setUserSetting(Usersetting usersetting) {
        if (userauthService.getByUsername(usersetting.getUsername()) == null)
            return new ResponseResult<>(403, "用户不存在");
        else return usersettingService.setUserSetting(usersetting);
    }

    @Operation(summary = "删除某个邮箱配置")
    @PostMapping("api/user/deletesetting")
    public ResponseResult<?> deleteUserSetting(String serverusername) {
        return usersettingService.deleteSetting(serverusername);
    }


}
