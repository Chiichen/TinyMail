package edu.scut.tinymail.utils.IMAP;

import io.swagger.models.auth.In;

import javax.swing.plaf.metal.MetalBorders;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Base64;

public class IMAP {

    private int serialnumber =1;
    private BufferedReader in;
    private PrintWriter out;

    private String response="";

    private boolean isInitialize=false;
    private boolean isLogin=false;

    private ArrayList<String> list=new ArrayList<>();

    private int numOfEmail;
    private String select_response="";


    private String plain_body="";

    private String image="";

    private String html_body="";

    private HashMap<Integer,String> subject_Map=new HashMap<>();
    private HashMap<Integer,String> from_Map=new HashMap<>();
    private HashMap<Integer,String> to_Map=new HashMap<>();
    private HashMap<Integer,String> date_Map=new HashMap<>();

    private HashMap<Integer,String> contentType_Map=new HashMap<>();
    private HashMap<Integer,String> boundary_Map=new HashMap<>();
    private HashMap<Integer,String> original_Map=new HashMap<>();
    private HashMap<Integer,String> plain_Map=new HashMap<>();
    private HashMap<Integer,String> HTML_Map=new HashMap<>();
    private HashMap<Integer,String> Image_Map=new HashMap<>();

    private HashMap<Integer,String> Application_Map=new HashMap<>();

    public  IMAP Initialize(String serverName,String serverPort){
        try{
            Socket socket=new Socket(serverName,Integer.parseInt(serverPort));
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            get_isInitialize();
            return this;
        }catch (InitializeException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return this;

    }

    public boolean get_isInitialize()throws InitializeException {
        try{
            response=in.readLine();
            if(response.contains("OK"))
                isLogin=true;
            else
                throw new InitializeException("初始化失败，请检查服务器名称，端口号是否正确，网络是否连接！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return this.isInitialize;
    }
    public  IMAP login(String username,String password){

        out.println("a"+String.valueOf(serialnumber)+" Login "+username+" "+password);
        serialnumber++;
        get_isLogin();
        return this;
    }

    public boolean get_isLogin(){
        try{
            response=in.readLine();
            if(response.contains("Success login"))
                isLogin=true;
            else
                throw new loginException("登录失败，账号或授权码错误");
        }catch (loginException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return this.isLogin;
    }

    public IMAP list(){
        out.println("a"+String.valueOf(serialnumber)+" LIST \"\" \"%\"");
        serialnumber++;
        return this;

    }
    public IMAP getList(){

        try{
            response=in.readLine();
            while(response.startsWith("*")){
                String str=response.substring(response.substring(0,response.lastIndexOf('"')-1).lastIndexOf('"')+1,response.lastIndexOf('"'));
                list.add(str);
                response = in.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public String get_list(int index){
        return list.get(index);
    }
    public  IMAP select(String folder){
        out.println("a"+String.valueOf(serialnumber)+" SELECT "+folder);
        serialnumber++;
        return this;
    }

    public String get_select_response(){
        try{
            response = in.readLine();
            while(response.startsWith("*")){
                if(response.contains("EXISTS"))
                    numOfEmail=Integer.parseInt(response.substring(2,response.indexOf("E")-1));
                select_response=select_response+response+"\n";
                response = in.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return select_response;
    }



    public int getNumOfEmail() {
        return numOfEmail;
    }

    public HashMap<Integer, String> getSubject_Map() {
        return subject_Map;
    }

    public HashMap<Integer, String> getFrom_Map() {
        return from_Map;
    }

    public HashMap<Integer, String> getTo_Map() {
        return to_Map;
    }

    public HashMap<Integer, String> getDate_Map() {
        return date_Map;
    }

    public HashMap<Integer, String> getContentType_Map() {
        return contentType_Map;
    }

    public HashMap<Integer, String> getBoundary_Map() {
        return boundary_Map;
    }

    public HashMap<Integer, String> getOriginal_Map() {
        return original_Map;
    }

    public HashMap<Integer, String> getPlain_Map() {
        return plain_Map;
    }

    public HashMap<Integer, String> getHTML_Map() {
        return HTML_Map;
    }

    public HashMap<Integer, String> getImage_Map() {
        return Image_Map;
    }

    public HashMap<Integer, String> getApplication_Map() {
        return Application_Map;
    }

    public  IMAP fetchOriginal(int index){
        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" RFC822");
        serialnumber++;
        String original="";
        try{
            response = in.readLine();
            while(!response.startsWith(")")){
                if(!response.startsWith("*")){
                    original=original+response+"\n";
                }
                response = in.readLine();
            }
            response=in.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }


    public  IMAP fetchHEADER_From(int index){
        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" BODY.PEEK[HEADER.FIELDS (From)]");
        serialnumber++;
        String from="";
        try{
            response = in.readLine();
            while(!response.startsWith(")")){
                if(!response.startsWith("*")){
                    from=from+response;
                }
                if(response.contains("Mails not exist")){
                    throw new NoemailException("不存在该封邮件！");
                }
                response = in.readLine();
            }
            if(from.toLowerCase().contains("utf-8")){
                from=Base64Decoder.decodeBase64Printable(from);
            }
            response=in.readLine();//处理a4 OK FETCH Completed
//            from=Base64Decoder.decodeBase64Printable(from);
            from_Map.put(index,from);
        }catch(NoemailException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }
    public  IMAP fetchHEADER_Subject(int index){
        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" BODY.PEEK[HEADER.FIELDS (Subject)]");
        serialnumber++;
        String subject="";
        try{

            response = in.readLine();
            while(!response.startsWith(")")){
                if(!response.startsWith("*")){
                    subject=subject+response+"";
                }

                response = in.readLine();
            }
            if(subject.toLowerCase().contains("utf-8")){
                subject=Base64Decoder.decodeBase64Printable(subject);
            }
            response=in.readLine();//处理 a OK FETCH Completed
//            subject=Base64Decoder.decodeBase64Printable(subject);
            subject_Map.put(index,subject);

        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public  IMAP fetchHEADER_Date(int index){

        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" BODY.PEEK[HEADER.FIELDS (Date)]");
        serialnumber++;
        String date="";
        try{

            response = in.readLine();
            while(!response.startsWith(")")){
                if(!response.startsWith("*")){
                    date=date+response+"";
                }

                response = in.readLine();
            }
            response=in.readLine();//处理a OK FETCH Completed
           if(date.contains("Date:")){
               date=date.substring(date.indexOf("Date:")+5);
               date=date.trim();
           }
            date_Map.put(index,date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public  IMAP fetchHEADER_To(int index){

        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" BODY.PEEK[HEADER.FIELDS (To)]");
        serialnumber++;
        String to="";
        try{

            response = in.readLine();
            while(!response.startsWith(")")){
                if(!response.startsWith("*")){
                    to=to+response+"";
                }

                response = in.readLine();
            }
            response=in.readLine();//处理a OK FETCH Completed
            to=to.substring(to.indexOf("To:")+3);
            to=to.trim();
            to_Map.put(index,to);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public  IMAP fetchHEADER_Boundary(int index){

        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" BODY.PEEK[HEADER.FIELDS (Content-Type)]");
        serialnumber++;
        String contentType="";
        String boundary="";
        try{

            response = in.readLine();
            while(!response.startsWith(")")){
                if(!response.startsWith("*")){
                    if(response.contains("Content-Type:")&&contentType==""){
                        contentType= response.substring(response.indexOf("Content-Type"),response.lastIndexOf(';'));
                        contentType=contentType.substring(contentType.indexOf("Content-Type:")+13);
                        contentType=contentType.trim();
                    }
                    if(response.toLowerCase().contains("boundary")&&boundary=="")
                    {
                        response=response+"\n";
                        int end;
                        if(response.substring(response.toLowerCase().indexOf("boundary=")).contains(";")){
                            end=response.indexOf("boundary=")+Math.min(response.substring(response.toLowerCase().indexOf("boundary=")).indexOf("\n"),response.substring(response.toLowerCase().indexOf("boundary=")).indexOf(";"));
                        }else {
                            end=response.indexOf("boundary=")+response.substring(response.toLowerCase().indexOf("boundary=")).indexOf("\n");
                        }

                        boundary=response.substring(response.toLowerCase().indexOf("boundary=")+9,end).replaceAll("\"","");
                    }
                }

                response = in.readLine();
            }
            boundary=boundary.replaceAll("\\\\","\\\\\\\\\\\\\\\\");
            boundary=boundary.replaceAll("\\+","\\\\\\\\+");
            boundary=boundary.replaceAll("\\*","\\\\\\\\*");
            boundary=boundary.replaceAll("\\.","\\\\\\\\.");

            response=in.readLine();//处理a OK FETCH Completed
            contentType_Map.put(index,contentType);
            boundary_Map.put(index,boundary);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }
    public  IMAP fetchHEADER_Text(int index){

        out.println("a"+String.valueOf(serialnumber)+" FETCH "+String.valueOf(index)+" BODY[TEXT]");
        serialnumber++;
        try{

            response = in.readLine();
            while(!response.startsWith(")")){
                if(!response.startsWith("*")){
                    plain_body= plain_body +response+"\n";
                }

                response = in.readLine();
            }
            response=in.readLine();//处理a OK FETCH Completed
           if(boundary_Map.get(index)!=""){
               String[] arr=plain_body.split(boundary_Map.get(index));
               String[] contenttype=new String[arr.length];
               String[] encodingtype=new String[arr.length];
               for(int i=0;i<arr.length;i++){
                   while(arr[i].startsWith("\n")){
                       arr[i]=arr[i].substring(1);
                   }
                   if(arr[i].endsWith("--")){
                       arr[i]=arr[i].substring(0,arr[i].length()-2);
                   }
                   if(arr[i].contains("Content-Type:")){
                       int end;
                       if(arr[i].contains(";")){
                           end=Math.min(arr[i].indexOf(';'),arr[i].indexOf('\n'));
                       }else{
                           end=arr[i].indexOf('\n');
                       }
                       contenttype[i]=arr[i].substring(arr[i].indexOf("Content-Type:")+13,end);

                   }

                   if(arr[i].contains("Content-Transfer-Encoding:")){
                       String sub=arr[i].substring(arr[i].indexOf("Content-Transfer-Encoding:"));
                       encodingtype[i]=arr[i].substring(arr[i].indexOf("Content-Transfer-Encoding:")+26,sub.indexOf('\n')+arr[i].indexOf("Content-Transfer-Encoding:"));
                   }
                   if(contenttype[i]!=null){
                       if(contenttype[i].contains("plain")){
                           String plain="";
                           if(encodingtype[i].toLowerCase().contains("quoted-printable")){
                               String qt=arr[i].substring(arr[i].indexOf("quoted-printable")+16);
                               while(qt.startsWith("\n")){
                                   qt=qt.substring(1);
                               }
                               plain=QuotedPrintableDecoder.decodeQuotedPrintable(qt);
                               if(plain.contains("-printable")){
                                   plain=plain.substring(plain.indexOf("-printable")+10);
                               }
                           }else if(encodingtype[i].toLowerCase().contains("base64")){
                               if(arr[i].endsWith("--"))
                                   arr[i]=arr[i].substring(0,arr[i].length()-2);
                               String str=(arr[i].substring(arr[i].indexOf("base64") + 6));
                               str=str.replace("\n","");
                               plain=Base64Decoder.decodeBase64(str);

                           }else if(encodingtype[i].toLowerCase().contains("8bit")){
                               if(arr[i].endsWith("--"))
                                   arr[i]=arr[i].substring(0,arr[i].length()-2);
                               String str=(arr[i].substring(arr[i].indexOf("8bit") + 4));
                               str=str.replace("\n","");
                               plain=Eight_BitDecoder.decoder(str);
                           }
                           while(plain.startsWith("\n")){
                               plain=plain.substring(plain.indexOf("\n")+1);
                           }
                           if(plain.endsWith("--")){
                               plain=plain.substring(0,plain.length()-2);
                           }
                           plain_Map.put(index,plain);
                       }else if(contenttype[i].contains("html")){
                           String html="";
                           if(encodingtype[i].toLowerCase().contains("quoted-printable")){
                               html=QuotedPrintableDecoder.decodeQuotedPrintable(arr[i]);
                               html=html.substring(html.indexOf("-printable")+10);
                           }else if(encodingtype[i].toLowerCase().contains("base64")){
                               if(arr[i].endsWith("--"))
                                   arr[i]=arr[i].substring(0,arr[i].length()-2);
                               String str=(arr[i].substring(arr[i].indexOf("base64") + 6));
                               str=str.replace("\n","");
                               html=Base64Decoder.decodeBase64(str);
                           }else if(encodingtype[i].toLowerCase().contains("8bit")){
                               if(arr[i].endsWith("--"))
                                   arr[i]=arr[i].substring(0,arr[i].length()-2);
                               String str=(arr[i].substring(arr[i].indexOf("8bit") + 4));
                               str=str.replace("\n","");
                               html=Eight_BitDecoder.decoder(str);
                           }
                           while(html.startsWith("\n")){
                               html=html.substring(html.indexOf("\n")+1);
                           }
                           if(html.endsWith("--")){
                               html=html.substring(0,html.length()-2);
                           }
                           HTML_Map.put(index,html);
                       }else if(contenttype[i].contains("image")){
                           String image="";
                           if(arr[i].contains("Content-Disposition")){
                               int start=arr[i].substring(arr[i].indexOf("Content-Disposition")).indexOf("\n")+arr[i].indexOf("Content-Disposition");
                               image=arr[i].substring(start);
                           }else{
                               int start=arr[i].substring(arr[i].indexOf("Content-Type")).indexOf("\n")+arr[i].indexOf("Content-Type");
                               image=arr[i].substring(start);
                           }

                           if(image.endsWith("--")){
                               image=image.substring(0,image.length()-2);
                           }
                           while(image.startsWith("\n")){
                               image=image.substring(1);
                           }
                           Image_Map.put(index,image);
                       }else if(contenttype[i].contains("application")){
                            String application="";
                           if(arr[i].contains("Content-Disposition")){
                               int start=arr[i].substring(arr[i].indexOf("Content-Disposition")).indexOf("\n")+arr[i].indexOf("Content-Disposition");
                               application=arr[i].substring(start);
                           }else{
                               int start=arr[i].substring(arr[i].indexOf("Content-Type")).indexOf("\n")+arr[i].indexOf("Content-Type");
                               application=arr[i].substring(start);
                           }

                           if(application.endsWith("--")){
                               application=application.substring(0,application.length()-2);
                           }
                           while(application.startsWith("\n")){
                               application=application.substring(1);
                           }
                           Application_Map.put(index,application);
                       }
                   }

               }
               plain_body="";
           }else{
               if(contentType_Map.get(index).contains("plain")){
                   plain_Map.put(index,plain_body);
               }else if(contentType_Map.get(index).contains("html")){
                   HTML_Map.put(index,Base64Decoder.decodeBase64Printable(plain_body));
               }else if(contentType_Map.get(index).contains("image")){
                   Image_Map.put(index,plain_body);
               }else if(contentType_Map.get(index).contains("application")){
                   Application_Map.put(index,plain_body);
               }else {
                   plain_Map.put(index,plain_body);
               }
                plain_body="";
           }


        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    //获取全部
    public IMAP getallheader(){



        list();
        getList();


        select("INBOX");
        get_select_response();
        for(int i=1;i<=getNumOfEmail();i++){
            fetchHEADER_From(i);
            fetchHEADER_Subject(i);
            fetchHEADER_Date(i);
            fetchHEADER_To(i);
        }
        return this;
    }

    //单独获取某一封
    public IMAP getone(int index){
        list();
        getList();


        select("INBOX");
        get_select_response();
        fetchHEADER_Boundary(index);
        fetchHEADER_Text(index);

        return this;
    }



}
