package edu.scut.tinymail.controller;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.exception.MailException;
import edu.scut.tinymail.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "MailController", description = "邮件接口")
@RestController
public class MailController {

    @Autowired
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
            Mail mail) throws IOException, MailException.SMTPException {
        return mailService.send(username, serverusername, mail);//send抛出异常后，由handler来接管，后面的return就不会执行。
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
            MultipartFile[] files) throws IOException, MailException.SMTPException {
        return mailService.attachedSend(username, serverusername, mail, files);
    }

    @Operation(summary = "获取邮件简介")
    @GetMapping("/api/mail/getinfo")
    public ResponseResult<?> getMails(String username, String serverusername, int pagenum) throws IOException, MailException.IMAPException {

        return mailService.getMails(username, serverusername, pagenum);
    }

    @Operation(summary = "获取邮件")
    @GetMapping("/api/mail/getdetail")
    public ResponseResult<?> getMailDetail(String username, String serverusername, int index) throws IOException, MailException.IMAPException {

        return mailService.getMailDetail(username, serverusername, index);
    }

    @Operation(summary = "获取邮件附件")
    @GetMapping("/api/mail/getAttachment")
    public void getMailAttachment(String username, String serverusername, int index, int attindex, HttpServletResponse response) throws IOException, MailException.IMAPException {

        mailService.getAttachment(username, serverusername, index, attindex, response);
    }


    @Operation(summary = "给前端的永真接口")
    @PostMapping("/api/ok")
    public ResponseResult<?> okForever(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseResult<>(200, "ok");
    }

}
