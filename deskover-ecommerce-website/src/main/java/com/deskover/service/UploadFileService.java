package com.deskover.service;

import com.deskover.util.storage.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    UploadFileResponse uploadAdminAvatar(MultipartFile avatarFile, String baseUrl);
}
