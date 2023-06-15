package edu.scut.tinymail.utils.IMAP;

public class decode {
    public  static String decoded(String str){
        if(str.contains("?Q?")){
            return QuotedPrintableDecoder.decodeQuotedPrintable(str);
        }else if(str.contains("?B?")){
            return Base64Decoder.decodeBase64Printable(str);
        }
        return str;
    }
}
