package edu.scut.tinymail.domain.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "邮件封装类")
@Data
public class Mail {
    @Schema(description = "邮件标题")
    String subject;
    @Schema(description = "邮件正文")
    String content;
    @Schema(description = "发件人地址")
    String fromAddress;
    @Schema(description = "收件人地址")
    String toAddress;

}
