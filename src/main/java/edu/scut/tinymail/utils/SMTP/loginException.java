package edu.scut.tinymail.utils.SMTP;

public class loginException extends Exception{

    static final long serialVersionUID = -70348386447698657L;

    public loginException(){
        super();
    }

    public loginException(String message) {
        super(message);
    }

}
