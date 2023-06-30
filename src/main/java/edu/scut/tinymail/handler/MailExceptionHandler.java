package edu.scut.tinymail.handler;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.exception.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MailExceptionHandler {

    @ExceptionHandler(MailException.SMTPException.class)
    @ResponseBody
    public ResponseResult<?> SMTPExceptionHandler(Exception ex) {
        ex.printStackTrace();
        return new ResponseResult<>(403, ex.getMessage());
    }

    @ExceptionHandler(MailException.IMAPException.class)
    @ResponseBody
    public ResponseResult<?> IMAPExceptionHandler(Exception ex) {
        ex.printStackTrace();
        return new ResponseResult<>(403, ex.getMessage());
    }

}
