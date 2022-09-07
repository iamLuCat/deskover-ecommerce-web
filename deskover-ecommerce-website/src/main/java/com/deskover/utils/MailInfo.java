package com.deskover.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MailInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String from;
    private String to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String body;
    private MultipartFile[] attachments;

    public MailInfo(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public MailInfo(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
