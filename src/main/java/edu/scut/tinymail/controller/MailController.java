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

import java.io.IOException;

@Tag(name = "MailController", description = "邮件接口")
@RestController
public class MailController {

    @Resource
    MailService mailService;

    @Operation(summary = "邮件发送接口(不含附件)")
    @PostMapping("/api/mail/send")
    //@PreAuthorize("hasAuthority('user')")
    public ResponseResult<?> sendMail(
            @Parameter(description = "发件的用户")
            String username,
            @Parameter(description = "用来发送邮件的smtp服务器")
            String serverusername,
            @Parameter(description = "需要发送的邮件信息")
            Mail mail) throws IOException {
        mailService.send(username, serverusername, mail);//send抛出异常后，由handler来接管，后面的return就不会执行。
        return new ResponseResult<>(200, "success", mail);
    }

    @Operation(summary = "邮件发送接口(有附件)")
    @PostMapping("/api/mail/attachedsend")
    //@PreAuthorize("hasAuthority('user')")
    public ResponseResult<?> sendAttachedMail(
            @Parameter(description = "发件的用户")
            String username,
            @Parameter(description = "用来发送邮件的smtp服务器")
            String serverusername,
            @Parameter(description = "需要发送的邮件信息")
            Mail mail,
            @Parameter(description = "需要发送的附件")
            MultipartFile[] files) throws IOException {
        System.out.println(files[0].getOriginalFilename());
        //send抛出异常后，由handler来接管，后面的return就不会执行。
        return mailService.attachedSend(username, serverusername, mail, files);
    }

    @Operation(summary = "获取邮件")
    @PostMapping("/api/getmail")
    public ResponseResult<?> gerMails(String username, String serverusername) {

        return mailService.getMails(username, serverusername);
    }


    @Operation(summary = "给前端的永真接口")
    @PostMapping("/api/ok")
    public ResponseResult<?> okForever(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseResult<>(200, "ok");
    }

}
