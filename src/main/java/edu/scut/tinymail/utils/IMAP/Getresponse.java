package edu.scut.tinymail.utils.IMAP;

import java.io.BufferedReader;

public class Getresponse {

    public static void readSelect(BufferedReader in,String response){
        try{
            while(response!=null){
                response = in.readLine();
                System.out.println(response);
                if(!(response.startsWith("*")))
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
