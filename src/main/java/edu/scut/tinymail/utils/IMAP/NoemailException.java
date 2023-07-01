package edu.scut.tinymail.utils.IMAP;

public class NoemailException extends Exception{
    static final long serialVersionUID = -7038386346769656657L;

    public NoemailException(){
        super();
    }

    public NoemailException(String message) {
        super(message);
    }
}
