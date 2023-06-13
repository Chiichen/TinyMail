package edu.scut.tinymail.service.impl;

import edu.scut.tinymail.domain.ResponseResult;
import edu.scut.tinymail.domain.entity.Mail;
import edu.scut.tinymail.service.MailService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MailServiceImpl implements MailService {


    @Override
    public ResponseResult<?> send(Mail mail) {

        return null;
    }

    /**
     * @param mail
     * @param files
     * @return
     */
    @Override
    public ResponseResult<?> attachedSend(Mail mail, MultipartFile[] files) throws IOException {


        return null;
    }
}
