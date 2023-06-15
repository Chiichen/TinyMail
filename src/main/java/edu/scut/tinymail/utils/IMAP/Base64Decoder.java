package edu.scut.tinymail.utils.IMAP;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Decoder {
    public static String decodeBase64Printable(String str){
        unequal(str);
        if(str.contains("?B?")){
            String[] arr=str.split("\\?");
            String charsetName= arr[1];
            int[] index=new int[10];
            int count=0;
            for(int i=0;i<arr.length;i++){

                if(((i+1)%4)==0){
                    index[count++]=i;

                }

            }
            String decodedText="";
            for(int i=0;i<count;i++){
                String encodeText=arr[index[i]];
                int length;
                if(encodeText.length()%4!=0) {
                    length = 4 - encodeText.length() % 4;
                    if(!encodeText.endsWith("=")) {

                        while (length != 0) {
                            encodeText = encodeText + "=";
                            length--;
                        }
                    }
                    else {
                        while(length!=4){
                            encodeText=encodeText.substring(0,encodeText.length()-1);
                            length++;
                        }
                    }


                }
                byte [] bytes= Base64.getDecoder().decode(encodeText);
                Charset charset= StandardCharsets.UTF_8;
                if("ISO-2022-JP".equalsIgnoreCase(charsetName)){
                    charset=Charset.forName("ISO-2022-JP");
                }

                decodedText =decodedText+new String(bytes,charset);

            }
            return decodedText;
        }
        else{
            byte[] decodedBytes = Base64.getMimeDecoder().decode(str.getBytes(StandardCharsets.ISO_8859_1));
            return new String(decodedBytes, StandardCharsets.UTF_8);
        }

    }

    public static String unequal(String str){
        if(str.length()%4!=0){
            int length = 4 - str.length() % 4;
            if(!str.endsWith("=")) {

                while (length != 0) {
                    str = str + "=";
                    length--;
                }
            }
            else {
                while(length!=4){
                    str=str.substring(0,str.length()-1);
                    length++;
                }
            }
        }
        return str;

    }
}
