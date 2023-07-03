package edu.scut.tinymail.utils;

import edu.scut.tinymail.utils.IMAP.Base64Decoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class DecodeTest {


    @Test
    public void context1() {
        String str = new String("=?UTF-8?B?5Lqs5Lic5bey5pS25Yiw5oKo55qE6K6i5Y2V44CQMjc3MDkzKio=?=\n" +
                " =?UTF-8?B?KioqKuOAke+8jOasoui/juaCqA==?=\n" +
                " =?UTF-8?B?6ZqP5pe25YWz5rOo6K6i5Y2V54q25oCB77yB?=");
        Pattern pattern1 = Pattern.compile("(?<=\\?((UTF-8)|(utf-8))\\?([bB])\\?)[^?]*(?=\\?=)");
        Matcher matcher1 = pattern1.matcher(str);
        str = "";
        int matchfirst = 0;
        while (matcher1.find(matchfirst)) {
            str += matcher1.group(0);
            matchfirst = matcher1.end();
        }
        System.out.println(str);
        Pattern pattern2 = Pattern.compile(".*?==|[^=]+$");
        Matcher matcher2 = pattern2.matcher(str);
        str = "";
        System.out.println(matcher2.groupCount());
        while (matcher2.find()) {
            System.out.println(matcher2.group());
//            byte[] decodedBytes = Base64.getDecoder().decode(matcher2.group());
//            str += new String(decodedBytes, StandardCharsets.UTF_8);
//            System.out.println(str);
        }

    }

    @Test
    public void context2() {
        String input = new String("=?UTF-8?B?5oKoIFN0ZWFtIOaEv+acm+WNleS4iueahCDlj4zkurrmiJDooYwg5Y+K5Y+m?=\n" +
                " =?UTF-8?B?5aSWIDEwIOS4qumhueebruato+WcqOeJueWNlu+8gQ==?=");
        System.out.println(Base64Decoder.decodeBase64Printable(input));
    }
}




