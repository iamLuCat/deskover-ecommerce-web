package com.deskover.controller.rest.api;

import com.deskover.model.entity.database.Order;
import com.deskover.model.entity.dto.security.payload.MessageResponse;
import com.deskover.model.entity.other.MailInfo;
import com.deskover.other.util.MailUtil;
import com.deskover.service.SessionService;
import com.deskover.service.impl.VerifyServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/test")
public class TestApi {
	@Autowired
    private MailUtil mailUtil;

    @GetMapping("/email")
    public ResponseEntity<?> test(@RequestParam String to) throws IllegalStateException, IOException {
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
