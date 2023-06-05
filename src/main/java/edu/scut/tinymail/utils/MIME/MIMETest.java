package edu.scut.tinymail.utils.MIME;

import jakarta.activation.MimetypesFileTypeMap;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;



public class MIMETest {
    @Test
    public void test() throws IOException {
        MIME();
    }

    public void MIME() throws IOException {
        // 邮件服务器地址和端口
        String smtpServer = "smtp.163.com";
        int port = 587;

        // 发件人、收件人、用户名和密码
        String from = "m15017686102@163.com";
        String to = "253221402@qq.com";
        String user = "m15017686102@163.com";
        String password = "KDONRCKHYQIJKRAF";

        // 邮件主题和正文
        String subject = "Subject of the Email";
        String body = "This is the body of the email";

        // 附件文件名和路径
        String attachmentFilename = "a.txt";
        String attachmentPath = "D:\\IDEA\\IdeaProjects\\mail-demo\\a.txt";

        // 连接到邮件服务器
        Socket socket = new Socket(smtpServer, port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // 读取服务器响应
        String response = reader.readLine();
        System.out.println(response);

        // 发送EHLO命令
        sendCommand(writer, reader, "EHLO " + smtpServer);

        // 发送AUTH LOGIN命令并进行身份验证
        sendCommand(writer, reader, "AUTH LOGIN");
        sendCommand(writer, reader, Base64.getEncoder().encodeToString(user.getBytes()));
        sendCommand(writer, reader, Base64.getEncoder().encodeToString(password.getBytes()));

        // 发送MAIL FROM命令
        sendCommand(writer, reader, "MAIL FROM:<" + from + ">");

        // 发送RCPT TO命令
        sendCommand(writer, reader, "RCPT TO:<" + to + ">");

        // 发送DATA命令
        sendCommand(writer, reader, "DATA");

        // 发送邮件头部信息
        writer.write("From: " + from + "\r\n");
        writer.write("To: " + to + "\r\n");
        writer.write("Subject: " + subject + "\r\n");

        // 添加MIME版本和内容类型信息
        writer.write("MIME-Version: 1.0\r\n");
        writer.write("Content-Type: multipart/mixed; boundary=\"boundary\"\r\n\r\n");

        // 添加邮件正文
        writer.write("--boundary\r\n");
        writer.write("Content-Type: text/plain; charset=\"UTF-8\"\r\n\r\n");
        writer.write(body + "\r\n\r\n");

        // 添加附件
        writer.write("--boundary\r\n");
        writer.write("Content-Type: text/plain; name=\"" + attachmentFilename + "\"\r\n");
        writer.write("Content-Disposition: attachment; filename=\"" + attachmentFilename + "\"\r\n\r\n");
    }

    private static void sendCommand(BufferedWriter writer, BufferedReader reader, String command) throws IOException {
        // 向服务器发送命令
        writer.write(command + "\r\n");
        writer.flush();

        // 读取服务器响应
        String response = reader.readLine();
        System.out.println(response);
    }


    public String getMimeType(String filePath) throws IOException {
        Path path = new File(filePath).toPath();
        String mimeType = Files.probeContentType(path);
        //assertEquals(mimeType, "image/png");
        return mimeType;
    }
}
