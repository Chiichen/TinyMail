package edu.scut.tinymail.exception;

import java.io.Serial;

public class MailException {


    public static class BadExecution extends BaseException {
        @Serial
        private static final long serialVersionUID = 3555714415375055302L;

        public BadExecution(String msg) {
            super(msg);
        }
    }

    public static class NoData extends BaseException {

        @Serial
        private static final long serialVersionUID = 2148191942142494580L;

        public NoData(String msg) {
            super(msg);
        }
    }

    public static class MoreData extends BaseException {

        @Serial
        private static final long serialVersionUID = -5851418017510236080L;

        public MoreData(String msg) {
            super(msg);
        }
    }

    public static class InvalidParam extends BaseException {
        @Serial
        private static final long serialVersionUID = 4235225697094262603L;

        public InvalidParam(String msg) {
            super(msg);
        }
    }

}
