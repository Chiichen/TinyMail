package edu.scut.tinymail.domain.entry;

import lombok.Data;

@Data
public class Mail {
    String header;
    String sender;
    String receiver;
    String content;
}
