package edu.scut.tinymail.utils.SMTP;

import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SMTPTest {
    @Test
    public void test(){
          smtp();
    }

    public void smtp(){
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
        //服务器端的回应
        String response;


        //创建客户端套接字并建立连接
        int serverPort=25;//SMTP使用25号端口
        try{




        Socket clientSocket=new Socket(mailServer,serverPort);
        //判断连接状态
        isConnect(clientSocket,mailServer,serverPort);



        //
        InputStream inputStream=clientSocket.getInputStream();
        OutputStream outputStream=clientSocket.getOutputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));

        //发送HELO，服务器将返回状态码250
            bufferedWriter.write("HELO "+mailServer+"\r\n");
            bufferedWriter.flush();

        String line;
//        while((line=bufferedReader.readLine())!=null){
//            if(line.startsWith("250 ")){
//                if(line.contains("STARTTLS")){
//                    //要求使用STARTTLS命令退出循环
//                    printWriter.println("STARTTLS");
//                    break;
//                }
//            }else if(line.startsWith("5")){
//                //发生错误并退出
//                throw new Exception("SMTP 错误: "+line);
//            }
//        }
//        response=bufferedReader.readLine();
//        System.out.println(response);


//        printWriter.println("STARTTLS");
//        response=bufferedReader.readLine();
//        System.out.println(response);
//        isSupportSTARTTLS(response);


//重新获取输入输出流
//       inputStream=sslSocket.getInputStream();
//       outputStream=sslSocket.getOutputStream();
//       bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
//       printWriter=new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);


        //发送“AUTH LOGIN”命令，验证身份。服务器返回状态码334。然后服务器等待用户输入信息
        //如果用户验证成功，服务器返回状态码235

            bufferedWriter.write("AUTH LOGIN\r\n");
            bufferedWriter.flush();

        //发送验证信息

            bufferedWriter.write(encodeUsername+"\r\n");
            bufferedWriter.flush();
//        response=bufferedReader.readLine();
            bufferedWriter.write(encodePassword+"\r\n");
            bufferedWriter.flush();

        //验证是否成功
//        isLogin(response);

        //发送MAIL FROM命令，并包含发件人邮箱
        bufferedWriter.write("MAIL FROM: <"+fromAdress+">\r\n");
        bufferedWriter.flush();
//        response=bufferedReader.readLine();

        //SMTP客户端发送一个RCPT（收件人）命令
        bufferedWriter.write("RCPT TO: <"+toAdress+">\r\n");
        bufferedWriter.flush();

//        isSend(response);

        //多个收件人
//            List<String> recipients = new ArrayList<String>();
//            recipients.add("recipient1@example.com");
//            recipients.add("recipient2@example.com");
//            for (String recipient : recipients) {
//                bufferedWriter.write("RCPT TO:<" + recipient + ">\r\n");
//                bufferedWriter.flush();
//            }


        //发送DATA命令，表示即将发送邮件内容，返回状态码354
        bufferedWriter.write("DATA\r\n");


        //编辑邮件信息，发送数据
                bufferedWriter.write("Subject: Test12\r\n");
//            printWriter.println("Context-type:text;charset=UTF-8");
//            printWriter.println("Content-Transfer-Encoding: base64");
                bufferedWriter.write("From: "+fromAdress+"\r\n");
                bufferedWriter.write("To: "+toAdress+"\r\n");
                bufferedWriter.write("\r\n");
                bufferedWriter.write(msg+"\r\n");
                bufferedWriter.write(".\r\n");
                bufferedWriter.flush();

//        response=bufferedReader.readLine();




        //发送QUIT命令，断开连接
            bufferedWriter.write("QUIT\r\n");
            bufferedWriter.flush();
//            bufferedWriter.write("QUIT\r\n");
//            bufferedWriter.flush();


        while((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }
//        while (true){
//            clientSocket.sendUrgentData(0xFF);
//            System.out.println("目前处于连接状态!");
//        }

        clientSocket.close();

    }catch (connectException e){
            e.printStackTrace();
//    }catch (loginException e){
//            e.printStackTrace();
//    }catch (sendException e){
//            e.printStackTrace();
//    }catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//    }catch (TLSException e){
//            e.printStackTrace();
    }catch(Exception e){
            e.printStackTrace();
    }

    }

    public void isConnect(Socket socket,String mailServer,int serverPort) throws connectException{
        if(!socket.isConnected()){
            throw new connectException("Connection failed to " + mailServer + ":" + serverPort);
        }
    }

    public void isSupportSTARTTLS(String response)throws TLSException{
        Pattern pattern=Pattern.compile("(\\d{3})\\s(.+)");
        Matcher matcher=pattern.matcher(response);
        if(matcher.matches()){
            //获取响应码和状态信息
            String code=matcher.group(1);
            String message=matcher.group(2);
            //根据响应代码的状态类别抛出异常
            if(code.startsWith("4")||code.startsWith("5")){
                throw new TLSException("TLS failed"+message);
            }
        }
    }
    public void isLogin(String response)throws loginException{
        Pattern pattern=Pattern.compile("(\\d{3})\\s(.+)");
        Matcher matcher=pattern.matcher(response);
        if(matcher.matches()){
            //获取响应码和状态信息
            String code=matcher.group(1);
            String message=matcher.group(2);
            //根据响应代码的状态类别抛出异常
        if(code.startsWith("4")||code.startsWith("5")){
            throw new loginException("Login failed"+message);
        }
//        else{
//            System.out.println("235 Authentication successful");
//        }
    }
    }

    public void isSend(String response)throws sendException{

        Pattern pattern=Pattern.compile("(\\d{3})\\s(.+)");
        Matcher matcher=pattern.matcher(response);
        if(matcher.matches()){
            //获取响应码和状态信息
            String code=matcher.group(1);
            String message=matcher.group(2);
            //根据响应代码的状态类别抛出异常
            if(code.startsWith("4")||code.startsWith("5")){
                throw new sendException("Recipient verification failed: "+message);
            }
        }
    }


}
