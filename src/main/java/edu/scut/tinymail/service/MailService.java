package edu.scut.tinymail.service;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.exception.MailException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface MailService {

    ResponseResult<?> send(String username, String serverusername, Mail mail) throws IOException, MailException.SMTPException;


    ResponseResult<?> attachedSend(String username, String serverusername, Mail mail, MultipartFile[] files) throws IOException, MailException.SMTPException;

    ResponseResult<?> getMails(String username, String serverusername);
}
