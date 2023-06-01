package edu.scut.tinymail.controller;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entry.Mail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

@Api("处理邮件请求")
public class MailController {
    @ApiOperation("处理发邮件请求")
    @PostMapping("/api/mail/send")
    @PreAuthorize("hasAuthority('user')")
    public ResponseResult<?> sendmail(Mail mail) {
        //Todo 发送邮件
        return new ResponseResult<>(200, "success", mail);
    }
}
