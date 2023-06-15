package edu.scut.tinymail.service.impl;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.domain.entity.Usersetting;
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
    public ResponseResult<?> send(String username, String smtpserver, Mail mail) throws IOException {
        if (usersettingService.getSetting(username, smtpserver) == null)
            return new ResponseResult<>(403, "用户没有配置该邮件服务器");
        else {
            if (usersettingService.getSetting(username, smtpserver).getType() != 0)
                return new ResponseResult<>(403, "该服务器不是smtp服务器");
            else {
                Usersetting setting = usersettingService.getSetting(username, smtpserver);
                MIME mime = new MIME();
                mime.Initialize(setting.getServername(), 25, setting.getDomain(), setting.getUsername(), setting.getServerpassword())
                        .sendInfo(mail.getFromAddress(), mail.getToAddress())
                        .sendDataStart(mail.getFromAddress(), mail.getToAddress(), mail.getSubject())
                        .sendContent(mail.getContent())
                        .sendDataEnd();
            }
        }


        return new ResponseResult<>(200, "ok");
    }

    /**
     * @param mail  要发送的邮件
     * @param files 要上传的文件列表
     * @return 根据结果返回result
     */
    @Override
    public ResponseResult<?> attachedSend(String username, String smtpserver, Mail mail, MultipartFile[] files) throws IOException {
        if (usersettingService.getSetting(username, smtpserver) == null)
            return new ResponseResult<>(403, "用户没有配置该邮件服务器");
        else {
            if (usersettingService.getSetting(username, smtpserver).getType() != 0)
                return new ResponseResult<>(403, "该服务器不是smtp服务器");
            else {
                Usersetting setting = usersettingService.getSetting(username, smtpserver);
                MIME mime = new MIME();
                mime.Initialize(setting.getServername(), 25, setting.getDomain(), setting.getUsername(), setting.getServerpassword())
                        .sendInfo(mail.getFromAddress(), mail.getToAddress())
                        .sendDataStart(mail.getFromAddress(), mail.getToAddress(), mail.getSubject())
                        .sendContent(mail.getContent());
                for (MultipartFile file : files) {
                    mime.sendAttachment(file.getOriginalFilename(), file.getBytes());
                }
                mime.sendDataEnd();
                return new ResponseResult<>(200, "邮件成功发送");
            }


        }
    }

    @Override
    public ResponseResult<?> getMails(String username, String smtpserver) {

        if (usersettingService.getSetting(username, smtpserver) == null)
            return new ResponseResult<>(403, "用户没有配置该邮件服务器");
        else {
            if (usersettingService.getSetting(username, smtpserver).getType() != 1)
                return new ResponseResult<>(403, "该服务器不是imap服务器");
            else {
                Usersetting setting = usersettingService.getSetting(username, smtpserver);
                //Todo 调用utils进行邮件接收
            }

            return new ResponseResult<>(200, "ok");
        }
    }
}