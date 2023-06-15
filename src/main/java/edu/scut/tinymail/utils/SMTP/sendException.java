package edu.scut.tinymail.utils.SMTP;

public class sendException extends Exception{

    static final long serialVersionUID = -3387513247869929948L;

    public sendException() {
        super();
    }

    public sendException(String message) {
        super(message);
    }
}
