package edu.scut.tinymail.utils.MIME;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;



public class MIMETest {
    @Test
    public void test() throws IOException {
        MIME();
    }

    public void MIME() throws IOException {
        final String SMTP_SERVER = "smtp.qq.com";
        final int SMTP_PORT = 25;

        final String USERNAME = "2530221402@qq.com";
        final String PASSWORD = "aaievxjsnpavebdi";

        try {
            // 创建Socket连接
            Socket socket = new Socket(SMTP_SERVER, SMTP_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 读取服务器响应
            String response = reader.readLine();
            System.out.println(response);

            // 发送SMTP命令
            sendCommand(writer, "HELO qq.com");//
            response = reader.readLine();
            System.out.println(response);

            // 发送身份验证命令
            sendCommand(writer, "AUTH LOGIN");
            response = reader.readLine();
            System.out.println(response);

            // 发送用户名
            sendCommand(writer, base64Encode(USERNAME));
            response = reader.readLine();
            System.out.println(response);

            // 发送密码
            sendCommand(writer, base64Encode(PASSWORD));
            response = reader.readLine();
            System.out.println(response);

            // 发送发件人信息
            sendCommand(writer, "MAIL FROM: <2530221402@qq.com>");
            response = reader.readLine();
            System.out.println(response);

            // 发送收件人信息
            sendCommand(writer, "RCPT TO: <13712922800@163.com>");
            response = reader.readLine();
            System.out.println(response);

            // 开始邮件内容
            sendCommand(writer, "DATA");
            response = reader.readLine();
            System.out.println(response);

            // 发送邮件头部
            writer.println("From: 2530221402@qq.com");
            writer.println("To:13712922800@163.com");
            writer.println("Subject: Test Email");
            writer.println("MIME-Version: 1.0");
            //writer.println("charset=\"UTF-8\"");
            writer.println("Content-Transfer-Encoding: base64");
            writer.println("Content-Type: multipart/mixed; boundary=boundary");
            writer.println();

            // 发送邮件正文
            writer.println("--boundary");
            writer.println("Content-Type: text/plain");
            writer.println();
            writer.println("This is the body of the email.");

            // 发送附件
            writer.println("--boundary");
            writer.println("Content-Type: image/jpeg");
            writer.println("Content-Transfer-Encoding: base64");
            writer.println("Content-Disposition: attachment; filename=\"test.jpg\"");//attachment inline
            writer.println();

            // 读取附件内容并进行Base64编码后发送
            Path attachmentPath = Paths.get("D:\\test.jpg");
            byte[] attachmentBytes = Files.readAllBytes(attachmentPath);
            String base64EncodedAttachment = Base64.getEncoder().encodeToString(attachmentBytes);
            writer.println(base64EncodedAttachment);
            //System.out.println(base64EncodedAttachment);

            // 结束邮件内容
            writer.println();
            writer.println("--boundary--");
            writer.println(".");
            writer.flush();

            response = reader.readLine();
            System.out.println(response);

            // 结束会话
            sendCommand(writer, "QUIT");
            response = reader.readLine();
            System.out.println(response);

            // 关闭连接
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendCommand(PrintWriter writer, String command) {
        writer.println(command);
        writer.flush();
    }

    private static String base64Encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public String getMimeType(String filePath) throws IOException {
        Path path = new File(filePath).toPath();
        String mimeType = Files.probeContentType(path);
        //assertEquals(mimeType, "image/png");
        return mimeType;
    }
}
