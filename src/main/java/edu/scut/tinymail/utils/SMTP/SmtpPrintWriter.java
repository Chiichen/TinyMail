package edu.scut.tinymail.utils.SMTP;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Store;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SmtpPrintWriter {
    @Test
    public void test() {
        smtpPrintWriter();
    }

    private void smtpPrintWriter() {
        String msg="\r\n This is a test email!";

        //选择一个邮件服务
        String mailServer="smtp.163.com";
        //发送方地址和接收方地址
        String fromAdress="m15017686102@163.com";
        String toAdress="1405522135@qq.com";
        //发送方，验证信息，由于邮箱输入信息会使用64编码，因此需要进行编码
        String username="m15017686102@163.com";
        String password="KDONRCKHYQIJKRAF";
        //对username和password进行Base64编码
        String encodeUsername=Base64.getEncoder().encodeToString(username.getBytes());
        String encodePassword=Base64.getEncoder().encodeToString(password.getBytes());
        int serverPort=25;//SMTP使用25号端口
        try{
            Socket clientSocket=new Socket(mailServer,serverPort);
            InputStream inputStream=clientSocket.getInputStream();
            OutputStream outputStream=clientSocket.getOutputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(outputStream),true);
            String line;

            line=bufferedReader.readLine();
            System.out.println(line);

            printWriter.println("HELO "+mailServer);

            line=bufferedReader.readLine();
            System.out.println(line);

            //如果服务器支持STARTTLS则发送STARTTLS命令
            if(line.startsWith("250")){
                System.out.println("1");

                //发送STARTTLS命令
                printWriter.println("STARTTlS");
                line=bufferedReader.readLine();
                System.out.println(line);

                //升级为TLS加密
                SSLSocketFactory sslSocketFactory=(SSLSocketFactory) SSLSocketFactory.getDefault();
                SSLSocket sslSocket=(SSLSocket) sslSocketFactory.createSocket(clientSocket,mailServer,587,true);
                sslSocket.setNeedClientAuth(false);
                sslSocket.startHandshake();

                line=bufferedReader.readLine();
                System.out.println(line);
                //重新创建输入输出流
                bufferedReader=new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                printWriter=new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));

                //进行SMTP身份验证
//                printWriter.println("EHLO"+mailServer);
//
//
//                line=bufferedReader.readLine();
//                System.out.println(line);

                printWriter.println("AUTH LOGIN");



                printWriter.println(encodeUsername);

                printWriter.println(encodePassword);
                //发送邮件内容
                printWriter.println("MAIL FROM:<"+fromAdress+">");
                printWriter.println("RCPT TO:<"+toAdress+">");
                printWriter.println("DATA");
                printWriter.println("Subject: Test12");
                printWriter.println("From: "+fromAdress+"");
                printWriter.println("To: "+toAdress+"");
                printWriter.println("");
                printWriter.println(msg);
                printWriter.println(".");

            }


            printWriter.println("QUIT");
            bufferedReader.close();
            printWriter.close();
            clientSocket.close();

    }catch (Exception e) {
            e.printStackTrace();
        }
}
}

