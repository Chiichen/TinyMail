package edu.scut.tinymail.exception;

import java.io.Serial;

public class MailException {


    public static class SMTPException extends BaseException {

        @Serial
        private static final long serialVersionUID = -6505082281969920053L;

        public SMTPException(String msg) {
            super(msg);
        }
    }

    public static class IMAPException extends BaseException {


        @Serial
        private static final long serialVersionUID = -838203214138370189L;

        public IMAPException(String msg) {
            super(msg);
        }
    }

}
