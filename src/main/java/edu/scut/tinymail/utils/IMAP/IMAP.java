package edu.scut.tinymail.utils.IMAP;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;


public class IMAP {

    private int serialnumber = 1;
    private BufferedReader in;
    private PrintWriter out;

    private String response = "";

    private String plain_body = "";

    private String image = "";

    private String html_body = "";
    @Setter
    @Getter
    private HashMap<Integer, String> plain_body_list = new HashMap<>();

    @Setter
    @Getter
    private HashMap<Integer, String> html_body_list = new HashMap<>();

    @Setter
    @Getter
    private HashMap<Integer, String> image_list = new HashMap<>();

    public IMAP Initialize(String serverName, String serverPort) {
        try {
            Socket socket = new Socket(serverName, Integer.parseInt(serverPort));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            return this;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;

    }
    public  IMAP login(String username,String password){

        out.println("a"+String.valueOf(serialnumber)+" Login "+username+" "+password);
        serialnumber++;
        return this;
    }

    public IMAP list(){
        out.println("a"+String.valueOf(serialnumber)+" LIST \"\" \"%\"");
        serialnumber++;
        return this;

    }
    public IMAP list(String folderName){
        out.println("a"+String.valueOf(serialnumber)+" FETCH 1:* (UID)");
        serialnumber++;
        return this;

    }
    public void getList(){

        try{
            response=in.readLine();
            while(response.startsWith("*")){
                response = in.readLine();
                System.out.println(response);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  IMAP select(){
        out.println("a"+String.valueOf(serialnumber)+" SELECT INBOX");
        serialnumber++;
        return this;
    }

    public void getResponse_Select(){
        try{
            response = in.readLine();
            while(response.startsWith("*")){
                response = in.readLine();
                System.out.println(response);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  IMAP search_unseen(){
        out.println("a"+String.valueOf(serialnumber)+" SEARCH UNSEEN");
        serialnumber++;
        return this;
    }

    public  IMAP fetch(int index){
        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" RFC822");
        serialnumber++;
        return this;
    }

    public IMAP fetchs(int[] index){
        for(int i=0;i<index.length;i++) {
            fetch(index[i]);
            System.out.println("第" + i + "封邮件");
            getBody();
            plain_body_list.put(index[i], plain_body);

            plain_body = "";
            html_body = "";
            image = "";
        }
        return this;
    }

    public void getBody(){
//        while(response!=null){
//            try{
//                response=in.readLine();
//                System.out.println(response);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
            String subject="";
            String from="";
            String date="";
            String to="";
            String content_type="";
            String boundary="";

            String Encoding_Type="";

            try{
                response=in.readLine();
                while(!response.startsWith(")")){
                    response=in.readLine();
//                    System.out.println(response);


                    date=date+getDate();
                    from=from+getFrom();
                    to=to+getTo();
                    subject=subject+getSubject();
                    if(content_type==""){
                        content_type=getContent_type();
                    }

                    if(content_type.contains("text/plain")){
                        plain_body=getPlain();
                    }else if(content_type.contains("multipart/alternative")&& response.contains(boundary)&&!boundary.equals("")){
                        response=in.readLine();

                        if(response.contains("text/plain")){
                            plain_body=get_Multipart_Plain(boundary);
                        }

                    }else  if(content_type.contains("multipart/mixed")&&response.contains(boundary)&&!boundary.equals("")){
                        response=in.readLine();
                        if(response.contains("text/plain")){
                            plain_body=get_Multipart_Plain(boundary);
                        }
                    }
                    if(response.contains("text/html")){
                        html_body = get_Multipart_HTML(boundary);
                    }
                    if (response.contains("image")) {
                        image = get_Image(boundary);
                    }
                    boundary = boundary + getBoundary();

//                    getHTML(html_body,boundary);

                }
//
//                System.out.println(decode.decoded(subject));
//                System.out.println("发件人: "+decode.decoded(from));
//                if(date!=""){
//                    System.out.println("时间: "+decode.decoded(date));
//                }
//                System.out.println("主题: "+decode.decoded(subject));
//                System.out.println("收件人: "+decode.decoded(to));
//                if(plain_body!=""){
//                    System.out.println("正文："+plain_body);
//                }
//                if(html_body!=""){
//                    System.out.println("HTML: "+html_body);
//                }
//                if(image!=""){
//                    System.out.println("Image: "+image);
//                }

            }catch (Exception e){

            }
    }



    public String getDate(){
        if(response.startsWith("Date: ")){
            return response.substring(6);
        }
        return "";
    }

    public String getFrom(){
        if(response.startsWith("From: ")){
            return response.substring(6);
        }
        return "";
    }

    public String getTo(){
        if(response.startsWith("To:")){
            return response.substring(3);
        }
        return "";
    }

    public String getSubject(){
        if(response.startsWith("Subject: ")){
            String subject="";
            subject=subject+response.substring(9);
            try{
                response=in.readLine();
                while(!response.startsWith("Mime-Version:")&&!response.startsWith("MIME-Version")){
                    subject=subject+response;
                    response=in.readLine();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return subject;
        }
        return "";
    }

    public String getContent_type(){
        if(response.contains("Content-Type:")){
            return response.substring(14);
        }
        return "";
    }


    public String getBoundary(){
        if(response.contains("boundary=")){
            String boundary= response.substring(response.indexOf("boundary")+9);
            if(boundary.endsWith("\";"))
                return boundary.substring(1,boundary.length()-2);
            else return boundary;
        }
        return "";
    }

    public String getPlain(){

            while(!response.startsWith(")")){
                try{
                    response=in.readLine();

                    if(response.startsWith("X-Auto-Response-Suppress:")){
                        response=in.readLine();
                        while(!response.startsWith(")")){
                            plain_body=plain_body+response+"\n";
                            response=in.readLine();
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            return QuotedPrintableDecoder.decodeQuotedPrintable(plain_body);

    }

    public String get_Multipart_Plain(String boundary){
        try{



                response=in.readLine();
                while(!response.contains(boundary)){
                    if(!response.contains("Content-Type")&&!response.contains("charset")&&!response.contains("Content-Transfer-Encoding")){
                        plain_body=plain_body+response+"\n";
                    }
                    response=in.readLine();

                }

        }catch (Exception e){
                e.printStackTrace();
            }
        return QuotedPrintableDecoder.decodeQuotedPrintable(plain_body);
        }

    public String get_Multipart_HTML(String boundary){

        try{
                response=in.readLine();
                while(!response.contains(boundary)){
                    if(!response.contains("Content-Type")&&!response.contains("charset")&&!response.contains("Content-Transfer-Encoding")){
                        html_body=html_body+response+"\n";
                    }
                    response=in.readLine();
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return QuotedPrintableDecoder.decodeQuotedPrintable(html_body);
    }

    public String get_Image(String boundary) {
        try{
            response=in.readLine();
            while(!response.contains(boundary)){
                if(!response.contains("Content-Disposition")&&!response.contains("filename")){
                    image=image+response+"\n";
                }
                response=in.readLine();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }


    public String getResponse(){
        try{
             response=in.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
