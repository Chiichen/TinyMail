package edu.scut.tinymail.utils.SMTP;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class SSLTest {
//    public static void main(String[] args) {
//        try {
//            // 创建 SSL 上下文
//            SSLContext sslContext = SSLContext.getInstance("SMTP");
//
//            // 初始化 SSL 上下文
//            sslContext.init(null, null, null);
//
//            // 获取 SSL 套接字工厂
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//            // 创建 SSL 套接字
//            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("www.example.com", 443);
//
//            // 获取 SSL 套接字的输入/输出流
//            PrintWriter writer = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream(), "UTF-8"));
//            BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream(), "UTF-8"));
//
//            // 向服务器发送数据
//            writer.println("GET / HTTP/1.1");
//            writer.println("Host: www.qq.com");
//            writer.println();
//            writer.flush();
//
//            // 从服务器读取数据
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // 关闭连接
//            sslSocket.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
