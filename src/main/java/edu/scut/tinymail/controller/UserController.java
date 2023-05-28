package edu.scut.tinymail.controller;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entry.Userauth;
import edu.scut.tinymail.service.UserauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("用户数据接口")
public class UserController {

    @Autowired
    UserauthService userauthService;

    @ApiOperation("注册接口")
    @PostMapping("api/user/register")
    public ResponseResult<?> register(Userauth userauth) {
        return userauthService.register(userauth);
    }
}
