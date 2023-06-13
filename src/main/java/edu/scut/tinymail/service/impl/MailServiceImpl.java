package edu.scut.tinymail.service.impl;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.domain.entity.Usersetting;
import edu.scut.tinymail.service.MailService;
import edu.scut.tinymail.service.UsersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    UsersettingService usersettingService;

    @Override
    public ResponseResult<?> send(String username, String smtpserver, Mail mail) {
        if (usersettingService.getSetting(username, smtpserver) == null)
            return new ResponseResult<>(403, "用户没有配置该邮件服务器");
        else {
            if (usersettingService.getSetting(username, smtpserver).getType() != 0)
                return new ResponseResult<>(403, "该服务器不是smtp服务器");
            else {
                //Todo 调用utils进行邮件发送
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
                //Todo 调用utils进行邮件发送
            }

            return new ResponseResult<>(200, "ok");
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