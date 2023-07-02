package edu.scut.tinymail.utils.IMAP;

import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class IMAPTest {



    @Test
    public void test(){
        IMAP();
    }

    public void IMAP(){
        int[] index={4,5,53};
            String serverPort="143";
            String serverName="imap.qq.com";
            String username="1405522135@qq.com";
            String password="fysbuutvccsbgdce";
            IMAP imap=new IMAP();
            String encodeUsername= Base64.getEncoder().encodeToString(username.getBytes());
            String encodePassword=Base64.getEncoder().encodeToString(password.getBytes());
            try {


             //   建立Socket连接到IMAP服务器的端口143（明文）
                //获取全部邮件
                imap.Initialize(serverName, serverPort).
                        login(username, password).getallheader().getplain(2).getAttachment(2);
                //获取某一封邮件的主题，发件人，收件人，时间，正文，html文本，图片八进制字节流，附件八进制字节流
//                    imap.getSubject_Map().get(index);
//                    imap.getFrom_Map().get(index);
//                    imap.getTo_Map().get(index);
//                    imap.getDate_Map().get(index);
//                    imap.getPlain_Map().get(index);
//                    imap.getHTML_Map().get(index);
//                    imap.getImage_Map().get(index);
//                    imap.getApplication_Map().get(index);




            }catch (Exception e){
                e.printStackTrace();
            }

    }



}
