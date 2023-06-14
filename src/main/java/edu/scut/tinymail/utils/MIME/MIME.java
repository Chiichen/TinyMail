package edu.scut.tinymail.utils.MIME;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Data
public class MIME {

    @Test
    public void test() throws IOException {
        MIMESENDER();
    }

    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    //SMTP服务器
    String SMTP_SERVER = "smtp.qq.com";
    //服务器域名
    String DOMAIN_NAME = "qq.com";
    //SMTP服务器端口
    int SMTP_PORT = 25;
    //发件人
    String USERNAME = "2530221402@qq.com";
    //发件人授权码/密码
    String PASSWORD = "aaievxjsnpavebdi";
    //发件人
    String SENDER = USERNAME;
    //收件人
    String RECEIVER = "1277719855@qq.com";//13712922800@163.com
    //邮件标题
    String SUBJECT = "TEST Email";
    //邮件正文
    String CONTENT = "你好";//This is the body of the email.
    //附件路径
    String ATTACHMENT_PATH = "D:\\test.jpg";
    //附件名
    String ATTACHMENT_FILENAME = "test.jpg";
    //MIME类型
    String MIME_TYPE = "";

    //MIME初始化接口
    public MIME Initialize(String server, int port, String domain, String username, String psw) throws IOException {
        //参数初始化
        setSMTP_SERVER(server);
        setSMTP_PORT(port);
        setDOMAIN_NAME(domain);
        setUSERNAME(username);
        setPASSWORD(psw);

        // 创建Socket连接
        setSocket(new Socket(this.SMTP_SERVER, this.SMTP_PORT));
        setReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        setWriter(new PrintWriter(new OutputStreamWriter(socket.getOutputStream())));

        // 读取服务器响应
        System.out.println(reader.readLine());

        // 发送SMTP命令
        sendCommand(writer, "HELO " + DOMAIN_NAME);
        System.out.println(reader.readLine());

        // 发送身份验证命令
        sendCommand(writer, "AUTH LOGIN");
        System.out.println(reader.readLine());

        // 发送用户名
        sendCommand(writer, base64Encode(this.USERNAME));
        System.out.println(reader.readLine());

        // 发送密码
        sendCommand(writer, base64Encode(this.PASSWORD));
        System.out.println(reader.readLine());
        return this;
    }

    public MIME sendInfo(String sender, String receiver) throws IOException {
        // 发送发件人信息
        sendCommand(writer, "MAIL FROM: <" + sender + ">");
        System.out.println(reader.readLine());

        // 发送收件人信息
        sendCommand(writer, "RCPT TO: <" + receiver + ">");
        System.out.println(reader.readLine());
        return this;
    }

    public MIME sendDataStart(String sender, String receiver, String subject) throws IOException {
        // 开始邮件内容
        sendCommand(writer, "DATA");
        System.out.println(reader.readLine());

        // 发送邮件头部
        sendCommand(writer,"From: " + sender);
        sendCommand(writer,"To: " + receiver);
        sendCommand(writer,"Subject: " + subject);
        sendCommand(writer,"MIME-Version: 1.0");
        sendCommand(writer,"Content-Transfer-Encoding: base64");
        sendCommand(writer,"Content-Type: multipart/mixed; boundary=boundary");
        sendCommand(writer,"");

       return this;
    }

    // 发送邮件正文
    public MIME sendContent(String content){
        sendCommand(writer,"--boundary");
        sendCommand(writer,"Content-Type: text/plain");
        sendCommand(writer,"");
        sendCommand(writer, content);
        return this;
    }

    public MIME sendAttachment(String filename, String filepath) throws IOException {
        // 发送附件
        sendCommand(writer, "--boundary");
        sendCommand(writer,"Content-Type: application/octet-stream");
        sendCommand(writer,"Content-Transfer-Encoding: base64");
        sendCommand(writer,"Content-Disposition: attachment;filename=\"" + filename + "\"");
        writer.println();

        // 读取附件内容并进行Base64编码后发送
        Path attachmentPath = Paths.get(filepath);
        byte[] attachmentBytes = Files.readAllBytes(attachmentPath);
        String base64EncodedAttachment = Base64.getEncoder().encodeToString(attachmentBytes);
        sendCommand(writer, base64EncodedAttachment);

        return this;
    }

    public MIME sendDataEnd() throws IOException {
        // 结束邮件内容
        writer.println();
        writer.println("--boundary--");
        writer.println(".");
        writer.flush();
        System.out.println(reader.readLine());

        // 结束会话
        sendCommand(writer, "QUIT");
        System.out.println(reader.readLine());

        // 关闭连接
        socket.close();

        return this;
    }

    public void MIMESENDER() {
        try {
            // 创建Socket连接
            Socket socket = new Socket(this.SMTP_SERVER, this.SMTP_PORT);
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
            sendCommand(writer, base64Encode(this.USERNAME));
            response = reader.readLine();
            System.out.println(response);

            // 发送密码
            sendCommand(writer, base64Encode(this.PASSWORD));
            response = reader.readLine();
            System.out.println(response);

            // 发送发件人信息
            sendCommand(writer, "MAIL FROM: <" + this.SENDER + ">");
            response = reader.readLine();
            System.out.println(response);

            // 发送收件人信息
            sendCommand(writer, "RCPT TO: <" + this.RECEIVER + ">");
            response = reader.readLine();
            System.out.println(response);

            // 开始邮件内容
            sendCommand(writer, "DATA");
            response = reader.readLine();
            System.out.println(response);

            // 发送邮件头部
            sendCommand(writer,"From: " + this.SENDER);
            sendCommand(writer,"To: " + this.RECEIVER);
            sendCommand(writer,"Subject: " + this.SUBJECT);
            sendCommand(writer,"MIME-Version: 1.0");
            sendCommand(writer,"Content-Transfer-Encoding: base64");
            sendCommand(writer,"Content-Type: multipart/mixed; boundary=boundary");
            sendCommand(writer,"");

            // 发送邮件正文
            sendCommand(writer,"--boundary");
            sendCommand(writer,"Content-Type: text/plain");
            sendCommand(writer,"");
            sendCommand(writer, this.CONTENT);

            // 发送附件
            sendCommand(writer, "--boundary");
            writer.println("Content-Type: application/octet-stream");
            writer.println("Content-Transfer-Encoding: base64");
            writer.println("Content-Disposition: attachment; " +
                    "filename=\"" + this.ATTACHMENT_FILENAME + "\"");//attachment OR inline
            writer.println();

            // 读取附件内容并进行Base64编码后发送
            Path attachmentPath = Paths.get(this.ATTACHMENT_PATH);
            byte[] attachmentBytes = Files.readAllBytes(attachmentPath);
            String base64EncodedAttachment = Base64.getEncoder().encodeToString(attachmentBytes);
            sendCommand(writer, base64EncodedAttachment);
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


}