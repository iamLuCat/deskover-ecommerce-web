package com.deskover.other.util;

import com.deskover.model.entity.other.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MailUtil {
    public final static String FROM = "Deskover Team Support";

    @Autowired
    JavaMailSender sender;

    @Autowired
    ServletContext app;

    private final List<MimeMessage> queue = new ArrayList<>();

    public void push(String to, String subject, String body) throws MessagingException {
        MailInfo mailInfo = new MailInfo(to, subject, body);
        this.push(mailInfo);
    }

    public void push(MailInfo mailInfo) throws MessagingException {
        // Tạo message
        MimeMessage message = sender.createMimeMessage();

        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getBody(), true);

        String from = mailInfo.getFrom() + "<name@example.com>";
        if(!from.isBlank()) {
            helper.setFrom(from);
            helper.setReplyTo(from);
        }
        String to = mailInfo.getTo();
        if (to != null && !to.isBlank()) {
            helper.setTo(to);
        }
        String[] cc = mailInfo.getCc();
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
        String[] bcc = mailInfo.getBcc();
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }

        // Nếu có file đính kèm thì thêm vào message
        MultipartFile[] attachments = mailInfo.getAttachments();
        if (attachments != null && attachments.length > 0) {
            String path = app.getRealPath("/uploads/");
            for (MultipartFile attachment : attachments) {
                if (attachment.isEmpty()) {
                    continue;
                }
                FileSystemResource file = new FileSystemResource(path + attachment.getOriginalFilename());
                helper.addAttachment(Objects.requireNonNull(attachment.getOriginalFilename()), file);
            }
        }

        // Gửi message đến SMTP server
        queue.add(message);
    }

    @Scheduled(fixedDelay = 500)
    public void run() {
        if (queue.size() > 0) {
            int success = 0, error = 0;
            while (!queue.isEmpty()) {
                MimeMessage message = queue.remove(0);
                try {
                    sender.send(message);
                    success++;
                } catch (Exception ex) {
                	System.out.println(ex);
                }
            }
            System.out.printf(">> Sent: %d, Error:%d\r\n", success, error);
        }
    }

}
