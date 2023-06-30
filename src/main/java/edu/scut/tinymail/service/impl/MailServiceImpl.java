package edu.scut.tinymail.service.impl;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.domain.entity.Usersetting;
import edu.scut.tinymail.exception.MailException;
import edu.scut.tinymail.service.MailService;
import edu.scut.tinymail.service.UsersettingService;
import edu.scut.tinymail.utils.MIME.MIME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    UsersettingService usersettingService;

    @Override
    public ResponseResult<?> send(String username, String serverusername, Mail mail) throws IOException, MailException.SMTPException {

        if (usersettingService.getSmtpSetting(username, serverusername).getCode() != 200)
            return usersettingService.getSmtpSetting(username, serverusername);
        else {
            Usersetting setting = usersettingService.getSmtpSetting(username, serverusername).getData();
            MIME mime = new MIME();
            mime.Initialize(setting.getServername(), 25, setting.getDomain(), setting.getServerusername(), setting.getServerpassword())
                    .sendInfo(mail.getFromAddress(), mail.getToAddress())
                    .sendDataStart(mail.getFromAddress(), mail.getToAddress(), mail.getSubject())
                    .sendContent(mail.getContent())
                    .sendDataEnd();
        }


        return new ResponseResult<>(200, "ok");
    }

    /**
     * @param mail  要发送的邮件
     * @param files 要上传的文件列表
     * @return 根据结果返回result
     */
    @Override
    public ResponseResult<?> attachedSend(String username, String serverusername, Mail mail, MultipartFile[] files) throws IOException, MailException.SMTPException {
        if (usersettingService.getSmtpSetting(username, serverusername).getCode() != 200)
            return usersettingService.getSmtpSetting(username, serverusername);
        else {
            if (files == null) return send(username, serverusername, mail);
            Usersetting setting = usersettingService.getSmtpSetting(username, serverusername).getData();
            MIME mime = new MIME();
            mime.Initialize(setting.getServername(), 25, setting.getDomain(), setting.getServerusername(), setting.getServerpassword())
                    .sendInfo(mail.getFromAddress(), mail.getToAddress())
                    .sendDataStart(mail.getFromAddress(), mail.getToAddress(), mail.getSubject())
                    .sendContent(mail.getContent());
            for (MultipartFile file : files) {
                mime.sendAttachment(file.getOriginalFilename(), file.getBytes());
                //Todo 某些情况下中文文件名会乱码，待修复
            }
            mime.sendDataEnd();
            return new ResponseResult<>(200, "邮件成功发送");
        }


    }

    @Override
    public ResponseResult<?> getMails(String username, String serverusername) {


        if (usersettingService.getImapSetting(username, serverusername).getCode() != 200)
            return usersettingService.getImapSetting(username, serverusername);
        else {
            Usersetting setting = usersettingService.getImapSetting(username, serverusername).getData();
            //Todo 调用utils进行邮件接收
        }

        return new ResponseResult<>(200, "ok");
    }

}