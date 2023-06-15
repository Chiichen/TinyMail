package edu.scut.tinymail.utils.IMAP;

import java.nio.charset.StandardCharsets;



public class QuotedPrintableDecoder {
    public static String decodeQuotedPrintable(String encodeText) {
//                =E4=BD=AO
        StringBuilder decodeText = new StringBuilder();
        int length = encodeText.length();
        int i=0;
        while(i<length){
            char ch = encodeText.charAt(i);
            if(ch=='='){
                if(i<length-2){
                    try {
                        char nextChar = encodeText.charAt(i + 1);
                        char nextnextChar = encodeText.charAt(i + 2);
                        int decodeByte=Integer.parseInt("" + nextChar + nextnextChar,16);
                        decodeText.append((char) decodeByte);
                        i+=3;
                    }catch (NumberFormatException e){
//                       encodeText.charAt(i)=='\r'||'\n'

                        i+=2;
                    }
                }
                else{
                    decodeText.append(ch);
                    i++;
                }
            }else{
                decodeText.append(ch);
                i++;
            }
        }

        return splitdecode(new String(decodeText.toString().getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8));
    }

    public static String splitdecode(String str){
        String[] parts=str.split("\\?Q\\?");
        StringBuilder stringBuilder=new StringBuilder();
        for(String part:parts){
            if(part.endsWith("?= =?UTF-8"))
                stringBuilder.append(part.substring(0,part.length()-10));
            else if(part.endsWith("?=")){
                stringBuilder.append(part.substring(0,part.length()-2));
            }else{
                stringBuilder.append(str);
            }
        }
        return stringBuilder.toString();
    }
}


