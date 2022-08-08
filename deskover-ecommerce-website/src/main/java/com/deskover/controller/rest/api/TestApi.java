package com.deskover.controller.rest.api;

import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.model.entity.other.MailInfo;
import com.deskover.other.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/test")
public class TestApi {
    @Autowired
    private MailUtil mailUtil;

    @GetMapping("/email")
    public ResponseEntity<?> test(@RequestParam String to) {
        System.out.println(to);
        try {
            MailInfo mailInfo = new MailInfo();
            mailInfo.setFrom(MailUtil.FROM);
            mailInfo.setTo(to);
            mailInfo.setSubject("Test");
            mailInfo.setBody("Hello World");
            mailUtil.push(mailInfo);
            return ResponseEntity.ok(new MessageResponse("Send email successfully"));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
