package com.deskover.api;

import com.deskover.service.UploadFileService;
import com.deskover.dto.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = "*")
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/upload-file/admin-avatar")
    public ResponseEntity<?> uploadAdminAvatar(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        try {
            UploadFile uploadFileResponse = uploadFileService.uploadAdminAvatar(file, baseUrl);
            return ResponseEntity.ok(uploadFileResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
