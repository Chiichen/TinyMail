package edu.scut.tinymail.service;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface MailService {

    ResponseResult<?> send(String username, String smtpserver, Mail mail);


    ResponseResult<?> attachedSend(String username, String smtpserver, Mail mail, MultipartFile[] files) throws IOException;

    ResponseResult<?> getMails(String username, String smtpserver);
}
