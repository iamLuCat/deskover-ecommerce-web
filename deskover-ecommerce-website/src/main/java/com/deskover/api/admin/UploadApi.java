package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.UploadFile;
import com.deskover.dto.ghtk.UrlGGStrogeResponDto;
import com.deskover.service.UploadFileService;
import com.deskover.util.FirebaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class UploadApi {
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            UploadFile uploadFileResponse = uploadFileService.uploadFileToTempFolder(file);
            return ResponseEntity.ok(uploadFileResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/upload-file/delete-temp-folder")
    public ResponseEntity<?> deleteTempFolder() {
        try {
            uploadFileService.removeTempFolder();
            return ResponseEntity.ok(new MessageResponse("Xoá thư mục tạm thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/firebase/upload-file")
    public ResponseEntity<?> uploadFileToFirebase(@RequestParam("file") MultipartFile files) {
        try {
            String fileName = files.getOriginalFilename();

            File file = FirebaseUtil.convertToFile(files, fileName);
            String TEMP_URL = FirebaseUtil.uploadFile(file, fileName);
            UrlGGStrogeResponDto url = new UrlGGStrogeResponDto();
            url.setUrl(TEMP_URL);
            if (url.getUrl() == null) {
                file.delete();
                return ResponseEntity.badRequest().body(new MessageResponse("Upload thất bại"));
            } else {
                url.setMessage("Upload thành công");
                file.delete();
                return ResponseEntity.ok(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/firebase/upload-files")
    public ResponseEntity<?> uploadFilesToFirebase(@RequestParam("files") MultipartFile[] files) {
        try {
            List<UrlGGStrogeResponDto> response = new ArrayList<>();
            for (MultipartFile multipartFile : files) {
                String fileName = multipartFile.getOriginalFilename();
                File file = FirebaseUtil.convertToFile(multipartFile, fileName);
                String TEMP_URL = FirebaseUtil.uploadFile(file, fileName);
                UrlGGStrogeResponDto url = new UrlGGStrogeResponDto();
                url.setUrl(TEMP_URL);
                if (url.getUrl() == null) {
                    url.setMessage("Upload thất bại");
                    response.add(url);
                    file.delete();
                } else {
                    url.setMessage("Upload thành công");
                    response.add(url);
                    file.delete();
                }
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
