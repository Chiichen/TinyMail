package edu.scut.tinymail.controller;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "MailController", description = "邮件接口")
@RestController
public class MailController {


    @Resource
    MailService mailService;

    @Operation(summary = "邮件发送接口(不含附件)")
    @PostMapping("/api/mail/send")
    //@PreAuthorize("hasAuthority('user')")
    public ResponseResult<?> sendMail(
            @Parameter(description = "需要发送的邮件信息")
            Mail mail) {
        mailService.send(mail);//Todo send抛出异常后，后面的return还会执行吗
        return new ResponseResult<>(200, "success", mail);
    }

    @Operation(summary = "邮件发送接口(有附件)")
    @PostMapping("/api/mail/attachedsend")
    //@PreAuthorize("hasAuthority('user')")
    public ResponseResult<?> sendAttachedMail(
            @Parameter(description = "需要发送的邮件信息")
            Mail mail,
            @Parameter(description = "需要发送的附件")
            MultipartFile file) {
        mailService.send(mail);//Todo send抛出异常后，后面的return还会执行吗
        return new ResponseResult<>(200, "success", mail);
    }


    @Operation(summary = "给前端的永真接口")
    @PostMapping("/api/ok")
    public ResponseResult<?> okForever(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseResult<>(200, "ok");
    }


}
