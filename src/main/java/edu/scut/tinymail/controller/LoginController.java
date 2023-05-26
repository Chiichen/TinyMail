package edu.scut.tinymail.controller;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.vo.UserauthVO;
import edu.scut.tinymail.service.UserauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    UserauthService userauthService;

    @PostMapping("/user/login")
    public ResponseResult<?> login(@RequestBody UserauthVO userauthVO){
        return userauthService.login(userauthVO);
    }
}
