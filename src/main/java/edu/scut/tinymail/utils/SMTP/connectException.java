package edu.scut.tinymail.utils.SMTP;

public class connectException extends Exception{

    static final long serialVersionUID = -703489023947698657L;

    public connectException() {
        super();
    }

    public connectException(String message) {
        super(message);
    }

}
