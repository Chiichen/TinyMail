package edu.scut.tinymail.service;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.exception.MailException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface MailService {

    ResponseResult<?> send(String username, String serverusername, Mail mail) throws IOException, MailException.SMTPException;


    ResponseResult<?> attachedSend(String username, String serverusername, Mail mail, MultipartFile[] files) throws IOException, MailException.SMTPException;

    ResponseResult<?> getMails(String username, String serverusername, int pagenum) throws IOException, MailException.IMAPException;

    ResponseResult<?> getMailDetail(String username, String serverusername, int index) throws IOException, MailException.IMAPException;


    void getAttachment(String username, String serverusername, int index, int attindex, HttpServletResponse response) throws IOException;
}
