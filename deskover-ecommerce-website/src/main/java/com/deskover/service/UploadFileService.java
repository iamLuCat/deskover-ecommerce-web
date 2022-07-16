package com.deskover.service;

import com.deskover.dto.UploadFile;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    UploadFile uploadAdminAvatar(MultipartFile file, String baseUrl);

    UploadFile uploadImageProduct(MultipartFile file, String baseUrl);
}
