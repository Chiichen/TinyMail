package edu.scut.tinymail.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailServerConfig {
    private String type;
    private String mailServer;
    private String username;
    private String password;
}
