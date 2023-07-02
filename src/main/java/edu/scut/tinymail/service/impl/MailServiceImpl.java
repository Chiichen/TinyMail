package edu.scut.tinymail.service.impl;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.domain.entity.Usersetting;
import edu.scut.tinymail.exception.MailException;
import edu.scut.tinymail.service.MailService;
import edu.scut.tinymail.service.UsersettingService;
import edu.scut.tinymail.utils.IMAP.IMAP;
import edu.scut.tinymail.utils.MIME.MIME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseResult<?> getMails(String username, String serverusername, int pagenum) throws IOException, MailException.IMAPException {

        System.out.println("username:" + username);
        System.out.println("serverusername:" + serverusername);
        if (usersettingService.getImapSetting(username, serverusername).getCode() != 200)
            return usersettingService.getImapSetting(username, serverusername);
        else {
            Usersetting setting = usersettingService.getImapSetting(username, serverusername).getData();
            String serverPort = "143";
            IMAP imap = new IMAP();
            //   建立Socket连接到IMAP服务器的端口143（明文）
            //获取全部邮件
            imap.Initialize(setting.getServername(), serverPort).
                    login(setting.getServerusername(), setting.getServerpassword()).getallheader(pagenum);
            //获取某一封邮件的主题，发件人，收件人，时间，正文，html文本，图片八进制字节流，附件八进制字节流
            List<Mail> mailList = new ArrayList<>();
            if (10 * pagenum - 10 > imap.getNumOfEmail()) throw new MailException.IMAPException("超出邮件范围");
            int lowerbound = 1, upperbound = 10;
            if (10 * pagenum > imap.getNumOfEmail()) {
                lowerbound = 1;
                upperbound = imap.getNumOfEmail() % 10;
            } else {
                lowerbound = (10 * pagenum - 10 + 1);
                upperbound = 10 * pagenum;
            }
            for (int index = lowerbound; index <= upperbound; index++) {
                mailList.add(new Mail(imap.getSubject_Map().get(index),
                        null,
                        imap.getFrom_Map().get(index),
                        imap.getTo_Map().get(index),
                        imap.getDate_Map().get(index)));
            }
            System.out.println(mailList);
            return new ResponseResult<>(200, "ok", mailList);


        }
    }

    /**
     * @param username
     * @param serverusername
     * @param index
     * @return
     */
    @Override
    public ResponseResult<?> getMailDetail(String username, String serverusername, int index) throws IOException {

        if (usersettingService.getImapSetting(username, serverusername).getCode() != 200)
            return usersettingService.getImapSetting(username, serverusername);
        else {
            Usersetting setting = usersettingService.getImapSetting(username, serverusername).getData();
            String serverPort = "143";
            IMAP imap = new IMAP();
            //   建立Socket连接到IMAP服务器的端口143（明文）
            imap.Initialize(setting.getServername(), serverPort).
                    login(setting.getServerusername(), setting.getServerpassword()).getplain(index);
            Mail mail;
            //获取某一封邮件的主题，发件人，收件人，时间，正文，html文本，图片八进制字节流，附件八进制字节流
            if (imap.getHTML_Map().get(index) != null) {
                mail = new Mail(imap.getSubject_Map().get(index),
                        imap.getHTML_Map().get(index),
                        imap.getFrom_Map().get(index),
                        imap.getTo_Map().get(index),
                        imap.getDate_Map().get(index));
            } else {
                mail = new Mail(imap.getSubject_Map().get(index),
                        imap.getPlain_Map().get(index),
                        imap.getFrom_Map().get(index),
                        imap.getTo_Map().get(index),
                        imap.getDate_Map().get(index));
            }
            return new ResponseResult<>(200, "ok", mail);
            //Todo 现在只能接收一个附件，待完善

        }
    }

    /**
     * @param username
     * @param serverusername
     * @param index
     * @return
     */
    @Override
    public ResponseResult<?> getAttachment(String username, String serverusername, int index) {

        if (usersettingService.getImapSetting(username, serverusername).getCode() != 200)
            return usersettingService.getImapSetting(username, serverusername);
        else {
            Usersetting setting = usersettingService.getImapSetting(username, serverusername).getData();
            String serverPort = "143";
            IMAP imap = new IMAP();
            //   建立Socket连接到IMAP服务器的端口143（明文）
            //获取全部邮件
            imap.Initialize(setting.getServername(), serverPort).
                    login(setting.getServerusername(), setting.getServerpassword()).getAttachment(index);
            //获取某一封邮件的主题，发件人，收件人，时间，正文，html文本，图片八进制字节流，附件八进制字节流


//            Mail mail = new Mail(imap.getSubject_Map().get(index),
//                    imap.getPlain_Map().get(index),
//                    imap.getFrom_Map().get(index),
//                    imap.getTo_Map().get(index),
//                    imap.getDate_Map().get(index));

            //Todo 调用utils进行邮件附件发送
            return new ResponseResult<>(200, "ok", null);
        }
    }

}