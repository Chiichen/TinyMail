package edu.scut.tinymail.utils.IMAP;

import org.junit.jupiter.api.Test;

import java.util.Base64;

public class IMAPTest {



    @Test
    public void test(){
        IMAP();
    }

    public void IMAP(){
        int[] index = {4, 5, 53};
        int[] index1 = {1};
        String serverPort = "143";
            String serverName="imap.qq.com";
            String username="1405522135@qq.com";
            String password="fysbuutvccsbgdce";
            IMAP imap=new IMAP();
            String encodeUsername= Base64.getEncoder().encodeToString(username.getBytes());
            String encodePassword=Base64.getEncoder().encodeToString(password.getBytes());
            try {
//                Socket socket=new Socket(serverName, Integer.parseInt(serverPort));

             //   建立Socket连接到IMAP服务器的端口143（明文）
                imap.Initialize(serverName,serverPort);


                imap.login(username, password);


                imap.list();


                imap.select();


//                imap.fetch(53);
///               imap.getBody();
                imap.fetch(1);

                imap.getHtml_body_list().get(6);

                //System.out.println(imap.get_Multipart_Plain(imap.getBoundary()));


//                IMAP.fetch(out,4);
//                response=in.readLine();
//                String subject=null;
//                String from=null;
//                String date=null;
//                String to=null;
//                String type=null;
//                String boundary=null;
//                String plain_body="";
//                String Encoding_Type="";
//                String html_body="";
//                while(!response.startsWith(")")){
//
//                    response=in.readLine();
//                    System.out.println(response);
//                    if(response.startsWith("From: ")){
//                        from= response.substring(6);
//                    }else if(response.startsWith("To: ")){
//                        to=response.substring(4);
//                    }else if(response.startsWith("Date: ")){
//                        date=response.substring(6);
//                    }else if(response.startsWith("Subject: ")){
//                        subject=response.substring(9);
//                        response=in.readLine();
//                        while(response.startsWith(" =?")){
//                            subject=subject+response.substring(1);
//                            response=in.readLine();
//                        }
//                    }else if(response.startsWith("Content-Type: multipart")){
//                        while(!response.startsWith(")")){
//                            if(response.contains("boundary")){
//                                boundary=response.substring(response.indexOf("boundary")+10,response.length()-2);
//                            }
//                            response=in.readLine();
//                            System.out.println(response);
//                            if(response.contains("Content-Type: text/plain")){
//                                response=in.readLine();
//                                System.out.println(response);
//                                while(!response.contains("Content-Transfer-Encoding: ")){
//                                    response=in.readLine();
//                                    Encoding_Type=response.substring(27);
//                                }
//                                while(!response.contains(boundary)){
//                                    if(!response.contains("charset")&&!response.contains("Content-Transfer-Encoding:")){
//                                        plain_body=plain_body+response+"\n";
//                                    }
//                                    response=in.readLine();
//                                    System.out.println(response);
//                                }
//                            }else if(response.contains("Content-Type: text/html")){
//                                response=in.readLine();
//                                while(!response.contains("Content-Transfer-Encoding: ")){
//                                    response=in.readLine();
//                                    Encoding_Type=response.substring(27);
//                                } while(!response.contains(boundary)){
//                                    if(!response.contains("charset")&&!response.contains("Content-Transfer-Encoding:")){
//                                        html_body=html_body+response+"\n";
//                                    }
//                                    response=in.readLine();
//                                }
//                            }
//                        }
//                    }else if(response.startsWith("Content-Type: text/plain")){
//                                while(!response.startsWith(")")){
//                                    response=in.readLine();
//                                    if(response.contains("X-Auto")){
//                                        response=in.readLine();
//                                        while(!response.startsWith(")")){
//                                            plain_body=plain_body+response+"\n";
//                                            response=in.readLine();
//                                        }
//                                    }
//                                }
//
//
//                    }
//
//                }
//
//                System.out.println(decode.decoded(subject));
//                System.out.println("发件人: "+decode.decoded(from));
//                System.out.println("时间: "+decode.decoded(date));
//                System.out.println("主题: "+decode.decoded(subject));
//                System.out.println("收件人: "+decode.decoded(to));
//
//                if(html_body!=""){
//                    System.out.println("HTML文本: "+QuotedPrintableDecoder.decodeQuotedPrintable(html_body));
//                }else if(plain_body!=""){
//                    System.out.println("正文: "+ QuotedPrintableDecoder.decodeQuotedPrintable(plain_body));
//                }
//
//                out.println("LOGOUT");
//                response = in.readLine();
//                in.close();
//                out.close();
//                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }

    }



}
