package edu.scut.tinymail.utils.MIME;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class MIME {
    @Test
    public void test() throws IOException {
        MIMESENDER();
    }

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


    public void MIMESENDER() throws IOException {
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

    /**
     * 获取
     *
     * @return SMTP_SERVER
     */
    public String getSMTP_SERVER() {
        return SMTP_SERVER;
    }

    /**
     * 设置
     *
     * @param SMTP_SERVER
     */
    public void setSMTP_SERVER(String SMTP_SERVER) {
        this.SMTP_SERVER = SMTP_SERVER;
    }

    /**
     * 获取
     *
     * @return SMTP_PORT
     */
    public int getSMTP_PORT() {
        return SMTP_PORT;
    }

    /**
     * 设置
     *
     * @param SMTP_PORT
     */
    public void setSMTP_PORT(int SMTP_PORT) {
        this.SMTP_PORT = SMTP_PORT;
    }

    /**
     * 获取
     *
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }

    /**
     * 设置
     *
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    /**
     * 获取
     *
     * @return PASSWORD
     */
    public String getPASSWORD() {
        return PASSWORD;
    }

    /**
     * 设置
     *
     * @param PASSWORD
     */
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    /**
     * 获取
     *
     * @return SENDER
     */
    public String getSENDER() {
        return SENDER;
    }

    /**
     * 设置
     *
     * @param SENDER
     */
    public void setSENDER(String SENDER) {
        this.SENDER = SENDER;
    }

    /**
     * 获取
     *
     * @return RECEIVER
     */
    public String getRECEIVER() {
        return RECEIVER;
    }

    /**
     * 设置
     *
     * @param RECEIVER
     */
    public void setRECEIVER(String RECEIVER) {
        this.RECEIVER = RECEIVER;
    }

    /**
     * 获取
     *
     * @return SUBJECT
     */
    public String getSUBJECT() {
        return SUBJECT;
    }

    /**
     * 设置
     *
     * @param SUBJECT
     */
    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    /**
     * 获取
     *
     * @return CONTENT
     */
    public String getCONTENT() {
        return CONTENT;
    }

    /**
     * 设置
     *
     * @param CONTENT
     */
    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    /**
     * 获取
     *
     * @return ATTACHMENT_PATH
     */
    public String getATTACHMENT_PATH() {
        return ATTACHMENT_PATH;
    }

    /**
     * 设置
     *
     * @param ATTACHMENT_PATH
     */
    public void setATTACHMENT_PATH(String ATTACHMENT_PATH) {
        this.ATTACHMENT_PATH = ATTACHMENT_PATH;
    }

    /**
     * 获取
     *
     * @return ATTACHMENT_FILENAME
     */
    public String getATTACHMENT_FILENAME() {
        return ATTACHMENT_FILENAME;
    }

    /**
     * 设置
     *
     * @param ATTACHMENT_FILENAME
     */
    public void setATTACHMENT_FILENAME(String ATTACHMENT_FILENAME) {
        this.ATTACHMENT_FILENAME = ATTACHMENT_FILENAME;
    }

    /**
     * 获取
     *
     * @return MIME_TYPE
     */
    public String getMIME_TYPE(Path path) throws IOException {
        return Files.probeContentType(path);
    }

    /**
     * 设置
     *
     * @param MIME_TYPE
     */
    public void setMIME_TYPE(String MIME_TYPE) {
        this.MIME_TYPE = MIME_TYPE;
    }

    /**
     * 获取
     *
     * @return DOMAINNAME
     */
    public String getDOMAIN_NAME() {
        return DOMAIN_NAME;
    }

    /**
     * 设置
     *
     * @param DOMAIN_NAME
     */
    public void setDOMAIN_NAME(String DOMAIN_NAME) {
        this.DOMAIN_NAME = DOMAIN_NAME;
    }
}