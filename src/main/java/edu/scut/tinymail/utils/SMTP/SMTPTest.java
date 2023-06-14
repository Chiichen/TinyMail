package edu.scut.tinymail.utils.SMTP;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Base64;

public class SMTPTest {
//    public static void main(String[] args) {
//        //1.建立SMTP连接
//        Socket smtpSocket=null;
//        try{
//            int port=25;
//            String host="smtp.example.com";
//            //创建并自动连接到服务器
//            smtpSocket=new Socket(host,25);
//            System.out.println("connect server success,in port"+port);
//            //关闭连接
//            smtpSocket.close();
//
//
//            //创建
//            smtpSocket=new Socket();
//            SocketAddress socketAddress=new InetSocketAddress(host,port);
//            //手动连接，并设置超时时间，如果在2s内连接成功就返回，
//            //如果在2s内出现其他异常则抛出该异常，，如果既没有其他异常也没有连接成功则抛出SocketTimeoutException异常
//            smtpSocket.connect(socketAddress);
//            System.out.println("connect server success,in port"+port);
//        }catch (UnknownHostException e){
//            e.printStackTrace();
//        }catch (ConnectException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if(smtpSocket!=null){
//                try{
//                    smtpSocket.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//        //2.获取输入输出流
//        InputStream inputStream=smtpSocket.getInputStream();
//        OutputStream outputStream=smtpSocket.getOutputStream();
//
//        //3.发送命令
//        OutputStreamWriter osw=new OutputStreamWriter(outputStream);
//        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
//
//        osw.write("EHLO example.com\n");
//        osw.flush();
//
//        String response=br.readLine();
//
//        //发送电子邮件
//        //认证
//        osw.write("AUTH LOGIN\n");
//        osw.flush();
//
//        response= br.readLine();
//        if(response.startsWith("334")){
//            osw.write(Base64.getEncoder().encodeToString("username".getBytes())+"\n");
//            osw.flush();
//            response= br.readLine();
//        }
//
//        if(response.startsWith("334")){
//            osw.write(Base64.getEncoder().encodeToString("password".getBytes())+"\n");
//            osw.flush();
//            response=br.readLine();
//        }
//
//        //发送邮件
//        osw.write("MAIL FROM:<sender@example.com>\n");
//        osw.flush();
//
//        response= br.readLine();
//
//        osw.write("RCPT TO:<recipient@example.com>\n");
//        osw.flush();
//
//        response= br.readLine();
//
//        osw.write("DATA\n");
//        osw.flush();
//
//        osw.write("Subject:Example email\n");
//        osw.write("Content-Type:text/plain;charset=\"us-ascii\"\n");
//        osw.write("Content-Transfer-Encoding:7bit\n");
//        osw.write("\n");
//        osw.write("This is an example email message.\n");
//        osw.write(".\n");
//        osw.flush();
//
//        response=br.readLine();
//
//        //关闭连接
//        osw.close();
//        br.close();
//        smtpSocket.close();
//
//
//    }
    @Test
    public void test(){
//        CreateAndConnect();
//        Create();
          smtp();
    }
//    public void CreateAndConnect(){
//        //1.建立SMTP连接
//            Socket smtpSocket=null;
////        try{
////            int port=465;
////            String host="smtp.qq.com";
////            //创建并自动连接到服务器
////            smtpSocket=new Socket(host,port);
////            //设置超时时间
////            smtpSocket.setSoTimeout(10000);
////            System.out.println("connect server success,in port "+port);
////            //关闭连接
////            smtpSocket.close();
////
////
////            //创建
////            smtpSocket=new Socket();
////            SocketAddress socketAddress=new InetSocketAddress(host,port);
////            //手动连接，并设置超时时间，如果在2s内连接成功就返回，
////            //如果在2s内出现其他异常则抛出该异常，，如果既没有其他异常也没有连接成功则抛出SocketTimeoutException异常
////            smtpSocket.connect(socketAddress);
////            System.out.println("connect server success,in port "+port);
////        }catch (UnknownHostException e){
////            e.printStackTrace();
////        }catch (ConnectException e){
////            e.printStackTrace();
////        }catch (IOException e) {
////            e.printStackTrace();
////        }
//////      finally {
//////            if(smtpSocket!=null){
//////                try{
//////                    smtpSocket.close();
//////                }catch (IOException e){
//////                    e.printStackTrace();
//////                }
//////            }
//////        }
//        try{
//            //创建一个SSL上下文
//            SSLContext sslContext=SSLContext.getDefault();
//
//
//            //建立SMTP连接
//            int port=465;
//            String host="smtp.qq.com";
//            //创建并自动连接到服务器
//            smtpSocket=new Socket(host,port);
//            //设置超时时间
//            smtpSocket.setSoTimeout(10000);
//            System.out.println("connect server success,in port "+port);
//
//
//            //创建
//            smtpSocket=new Socket();
//            SocketAddress socketAddress=new InetSocketAddress(host,port);
//            //手动连接，并设置超时时间，如果在2s内连接成功就返回，
//            //如果在2s内出现其他异常则抛出该异常，，如果既没有其他异常也没有连接成功则抛出SocketTimeoutException异常
//            smtpSocket.connect(socketAddress);
//            System.out.println("connect server success,in port "+port);
//
//            //使用STARTTLS命令开始TLS加密
//            InputStream inputStream=smtpSocket.getInputStream();
//            OutputStream outputStream=smtpSocket.getOutputStream();
//            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
//            PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);
//
//            printWriter.println("EHLO "+host);
//            String line;
//            while((line =reader.readLine())!=null){
//                System.out.println(line);
//                if(line.contains("250 ")){
//                    //SMTP服务器支持STARTTLS命令
//                    System.out.println("STARTTLS");
//                    printWriter.println("STARTTLS");
//                    break;
//                }
//            }
//
//            //启用TLS加密
////            SSLSocketFactory sslSocketFactory=sslContext.getSocketFactory();
////            SSLSocket sslSocket=(SSLSocket)sslSocketFactory.createSocket(smtpSocket,host,port,true);
//
//            //重新获取输入输出流
////            inputStream=sslSocket.getInputStream();
////            outputStream=sslSocket.getOutputStream();
////            reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
////            printWriter=new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"),true);
//
//            //发送登录信息
//            String userName="1405522135";
//            String password="Taq1314520.";
//            printWriter.println("AUTH_LOGIN");
//            printWriter.println(Base64.getEncoder().encodeToString(userName.getBytes()));
//            printWriter.println(Base64.getEncoder().encodeToString(password.getBytes()));
//
//            //发送邮件
//            printWriter.println("MAIL FROM:<941005485@qq.com>");
//            printWriter.println("RCPT TO:<1405522135@qq.com>");
//            printWriter.println("DATA");
//            printWriter.println("Subject: 测试邮件");
//            printWriter.println("Content-Type:text/html;charset=UTF-8");
//            printWriter.println();
//            printWriter.println("This is a test email!");
//
//            //将邮件正文Base64编码并写入输出流
////            String messageBody="测试测试";
////            byte[] messageBytes=messageBody.getBytes(StandardCharsets.UTF_8);
////            String base64Body=Base64.getEncoder().encodeToString(messageBytes);
////            printWriter.println(base64Body);
//
//            //发送结束标记
//            printWriter.println(".");
//            printWriter.flush();
////            printWriter.println("QUIT");
//            //检查SMTP服务器响应
//            String responseCode;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//                responseCode=line.substring(0,3);
//                if (responseCode.equals("250")) {
//                    System.out.println("Email sent successfully!");
//                    break;
//                } else if (responseCode.equals("554")) {
//                    System.out.println("Email address is not valid or is blacklisted!");
//                    break;
//                } else if (responseCode.startsWith("4")) {
//                    System.out.println("Temporary error occurred, email sending failed!");
//                    break;
//                } else if (responseCode.startsWith("5")) {
//                    System.out.println("Permanent error occurred, email sending failed!");
//                    break;
//                }
//            }
//        }catch(NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//
//    }
//    public void Create(){
//       try{
//           // 创建一个 SSL 上下文
//           SSLContext sslContext = SSLContext.getInstance("TLS");
//           sslContext.init(null,null,null);
//
//
//        // 建立 SMTP 连接
//           Socket smtpSocket = new Socket("smtp.qq.com", 465);
//           smtpSocket.setSoTimeout(10000); // 设置超时时间
//
//        // 使用 STARTTLS 命令开始 TLS 加密
//           InputStream inputStream = smtpSocket.getInputStream();
//           OutputStream outputStream = smtpSocket.getOutputStream();
//           BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//           PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
//
//           writer.println("EHLO smtp.qq.com");
//           String line;
//           while ((line = reader.readLine()) != null) {
//               System.out.println(line);
//               if (line.contains("250 ")) {
//                   // SMTP 服务器支持 STARTTLS 命令
//                   writer.println("STARTTLS");
//                   break;
//               }
//           }
//
//           // 启用 TLS 加密
//           SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//           SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(smtpSocket, "smtp.qq.com", 465, true);
//
//           // 重新获取输入输出流
//           inputStream = sslSocket.getInputStream();
//           outputStream = sslSocket.getOutputStream();
//           reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream(), "UTF-8"));
//           writer = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream(), "UTF-8"), true);
//
//           // 发送登录信息
//           writer.println("AUTH LOGIN");
//           writer.println(Base64.getEncoder().encodeToString("你的 QQ 邮箱用户名".getBytes()));
//           writer.println(Base64.getEncoder().encodeToString("QQ 邮箱授权码".getBytes()));
//
//           // 发送邮件
//           writer.println("MAIL FROM:<你的 QQ 邮箱地址>");
//           writer.println("RCPT TO:<收件人邮箱地址>");
//           writer.println("DATA");
//           writer.println("Subject: 你的邮件主题");
//           writer.println("Content-Type:text/html;charset=UTF-8");
//           writer.println("Content-Transfer-Encoding: base64");
//           writer.println();
//           //检查
//           String responseCode;
//           while ((line = reader.readLine()) != null) {
//               System.out.println(line);
//               responseCode = line.substring(0, 3);
//               if (responseCode.equals("250")) {
//                   System.out.println("Email sent successfully!");
//                   break;
//               } else if (responseCode.equals("554")) {
//                   System.out.println("Email address is not valid or is blacklisted!");
//                   break;
//               } else if (responseCode.startsWith("4")) {
//                   System.out.println("Temporary error occurred, email sending failed!");
//                   break;
//               } else if (responseCode.startsWith("5")) {
//                   System.out.println("Permanent error occurred, email sending failed!");
//                   break;
//               }
//           }
//// 将邮件正文 Base64 编码并写入输出流
//           String messageBody = "这是你的邮件正文";
//           byte[] messageBytes = messageBody.getBytes(StandardCharsets.UTF_8);
//           String base64Body = Base64.getEncoder().encodeToString(messageBytes);
//           writer.println(base64Body);
//
//// 发送结束标记
//           writer.println(".");
//           writer.flush();
//           writer.println("QUIT");
//
//// 关闭连接
//           smtpSocket.close();
//       }catch (Exception e){
//           e.printStackTrace();
//       }
//    }
    public void smtp(){
        String msg = "\r\n This is a test email!";
        //String endmsg="\r\n.\r\n";
        //选择一个邮件服务
        String mailServer="smtp.163.com";
        //发送方地址和接收方地址
        String fromAdress="m15017686102@163.com";
        String toAdress="1405522135@qq.com";
        //发送方，验证信息，由于邮箱输入信息会使用base64编码，因此需要进行编码
        String username="m15017686102@163.com";
        String password="KDONRCKHYQIJKRAF";
        //对username和password进行Base64编码
        String encodeUsername=Base64.getEncoder().encodeToString(username.getBytes());
        String encodePassword=Base64.getEncoder().encodeToString(password.getBytes());

        //创建客户端套接字并建立连接
        int serverPort=25;//SMTP使用25号端口
        try{
        Socket clientSocket=new Socket(mailServer,serverPort);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String line;
        //发送HELO，服务器将返回状态码250
        bufferedWriter.write("HELO 163.com\r\n");
        bufferedWriter.flush();
        //发送“AUTH LOGIN”命令，验证身份。服务器返回状态码334。然后服务器等待用户输入信息
        //如果用户验证成功，服务器返回状态码235
        bufferedWriter.write("AUTH LOGIN\r\n");
        bufferedWriter.flush();

            //发送验证信息
        bufferedWriter.write(encodeUsername+"\r\n");
        bufferedWriter.flush();
        bufferedWriter.write(encodePassword+"\r\n");
        bufferedWriter.flush();

        //发送MAIL FROM命令，并包含发件人邮箱


        bufferedWriter.write("MAIL FROM: <"+fromAdress+">\r\n");
        bufferedWriter.flush();

        //SMTP客户端发送一个或多个RCPT（收件人）命令
        bufferedWriter.write("RCPT TO: <"+toAdress+">\r\n");
        bufferedWriter.flush();

        //发送DATA命令，表示即将发送邮件内容，返回状态码354
        bufferedWriter.write("DATA\r\n");
        bufferedWriter.flush();

        //编辑邮件信息，发送数据
            bufferedWriter.write("Subject: Test\r\n");
            bufferedWriter.write("From: sender@example.com\r\n");
            bufferedWriter.write("To: recipient@example.com\r\n");
            bufferedWriter.write("\r\n");
            bufferedWriter.write("This is a test message.\r\n");
            bufferedWriter.write(".\r\n");
            bufferedWriter.flush();

        //发送QUIT命令，断开连接
            bufferedWriter.write("QUIT\r\n");
            bufferedWriter.flush();


        while((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }
//        while (true){
//            clientSocket.sendUrgentData(0xFF);
//            System.out.println("目前处于连接状态!");
//        }

        clientSocket.close();
    }catch (Exception e){
            System.out.println("目前处于断开状态!");
            e.printStackTrace();
        }
    }

}
