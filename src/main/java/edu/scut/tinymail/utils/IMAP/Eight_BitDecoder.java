package edu.scut.tinymail.utils.IMAP;

import java.io.UnsupportedEncodingException;

public class Eight_BitDecoder {
    public static String decoder(String encodedContent){
        String decodedContent=new String();
        try {
            decodedContent = new String(encodedContent.getBytes("UTF-8"), "UTF-8");
            System.out.println(decodedContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedContent;
    }

}
