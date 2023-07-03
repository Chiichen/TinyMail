package edu.scut.tinymail.utils.MIME;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Data
public class MIME {

    @Test
    public void test() throws IOException {

        MIME mime = new MIME();
        mime.Initialize("smtp.qq.com",25,"qq.com","2530221402@qq.com","aaievxjsnpavebdi")
                .sendInfo("2530221402@qq.com","2530221402@qq.com")//2900807385@qq.com
                .sendDataStart("2530221402@qq.com","2530221402@qq.com","Test")
                .sendContent("""
                        先帝创业未半而中道崩殂，今天下三分，益州疲弊，此诚危急存亡之秋也。然侍卫之臣不懈于内，忠志之士忘身于外者，盖追先帝之殊遇，欲报之于陛下也。诚宜开张圣听，以光先帝遗德，恢弘志士之气，不宜妄自菲薄，引喻失义，以塞忠谏之路也。
                        宫中府中，俱为一体，陟罚臧否，不宜异同。若有作奸犯科及为忠善者，宜付有司论其刑赏，以昭陛下平明之理，不宜偏私，使内外异法也。
                        侍中、侍郎郭攸之、费祎、董允等，此皆良实，志虑忠纯，是以先帝简拔以遗陛下。愚以为宫中之事，事无大小，悉以咨之，然后施行，必能裨补阙漏，有所广益。
                        将军向宠，性行淑均，晓畅军事，试用于昔日，先帝称之曰能，是以众议举宠为督。愚以为营中之事，悉以咨之，必能使行阵和睦，优劣得所。
                        亲贤臣，远小人，此先汉所以兴隆也；亲小人，远贤臣，此后汉所以倾颓也。先帝在时，每与臣论此事，未尝不叹息痛恨于桓、灵也。侍中、尚书、长史、参军，此悉贞良死节之臣，愿陛下亲之信之，则汉室之隆，可计日而待也。
                        臣本布衣，躬耕于南阳，苟全性命于乱世，不求闻达于诸侯。先帝不以臣卑鄙，猥自枉屈，三顾臣于草庐之中，咨臣以当世之事，由是感激，遂许先帝以驱驰。后值倾覆，受任于败军之际，奉命于危难之间，尔来二十有一年矣。
                        先帝知臣谨慎，故临崩寄臣以大事也。受命以来，夙夜忧叹，恐托付不效，以伤先帝之明，故五月渡泸，深入不毛。今南方已定，兵甲已足，当奖率三军，北定中原，庶竭驽钝，攘除奸凶，兴复汉室，还于旧都。此臣所以报先帝而忠陛下之职分也。至于斟酌损益，进尽忠言，则攸之、祎、允之任也。
                        愿陛下托臣以讨贼兴复之效，不效，则治臣之罪，以告先帝之灵。若无兴德之言，则责攸之、祎、允等之慢，以彰其咎；陛下亦宜自谋，以咨诹善道，察纳雅言，深追先帝遗诏，臣不胜受恩感激。
                        今当远离，临表涕零，不知所言。""")
//                .sendAttachment("test.jpg","D:\\test.jpg").sendAttachment("test.txt","D:\\test.txt")
//                .sendAttachment("test.rar","D:\\test.rar").sendAttachment("test.doc","D:\\test.doc")
//                .sendAttachment("test.pdf","D:\\test.pdf").sendAttachment("test.xls","D:\\test.xls")
                .sendAttachment("忠志之士忘身于外者盖追先帝之殊遇欲报之于陛下也。诚宜开张圣听以塞忠谏之路也.jpg",Files.readAllBytes(Paths.get("D:\\test.jpg")))
                .sendAttachment("忠志之士忘身于外者盖追先帝之殊遇欲报之于陛下也 诚宜开张圣听以光先帝遗德.rar",Files.readAllBytes(Paths.get("D:\\test.rar")))
                .sendAttachment("test.doc",Files.readAllBytes(Paths.get("D:\\test.doc")))
                .sendAttachment("test.xls",Files.readAllBytes(Paths.get("D:\\test.xls")))
                .sendAttachment("test.pdf",Files.readAllBytes(Paths.get("D:\\test.pdf")))
                .sendDataEnd();
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
    String RECEIVER = "13712922800@163.com";
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
        sendCommand(writer,"Date: " + getFormatted());
        sendCommand(writer,"Content-Transfer-Encoding: base64");
        sendCommand(writer,"Content-Type: multipart/mixed; boundary=boundary");
        sendCommand(writer,"");

       return this;
    }

    private static String getFormatted() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.of("+8"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z '('z')'");
        return now.format(formatter);
    }

    // 发送邮件正文
    public MIME sendContent(String content){
        sendCommand(writer,"--boundary");
        sendCommand(writer,"Content-Type: text/plain");
        sendCommand(writer,"");
        sendCommand(writer, content);
        return this;
    }

    public MIME sendAttachment(String filename, byte[] attachmentBytes) throws IOException {
        // 发送附件
        sendCommand(writer, "--boundary");
        sendCommand(writer,"Content-Type: application/octet-stream");
        sendCommand(writer,"Content-Disposition: attachment;filename=\"" + filename + "\"");
        sendCommand(writer,"Content-Transfer-Encoding: base64");
        writer.println();

        // 读取附件内容并进行Base64编码后发送
        //Path attachmentPath = Paths.get(filepath);
        //byte[] attachmentBytes = Files.readAllBytes(attachmentPath);
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
            sendCommand(writer, "HELO");//
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

            // 发送附件2
            setATTACHMENT_PATH("D:\\test.txt");
            setATTACHMENT_FILENAME("test.txt");
            sendCommand(writer, "--boundary");
            writer.println("Content-Type: application/octet-stream");
            writer.println("Content-Disposition: attachment; " +
                    "filename=\"" + this.ATTACHMENT_FILENAME + "\"");//attachment OR inline
            writer.println("Content-Transfer-Encoding: base64");
            writer.println();

            // 读取附件内容并进行Base64编码后发送
            attachmentPath = Paths.get(this.ATTACHMENT_PATH);
            attachmentBytes = Files.readAllBytes(attachmentPath);
            base64EncodedAttachment = Base64.getEncoder().encodeToString(attachmentBytes);
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