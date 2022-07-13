package com.deskover.service.impl;

import com.deskover.service.UploadFileService;
import com.deskover.util.storage.UploadFileResponse;
import com.deskover.util.storage.exception.MyFileNotFoundException;
import com.deskover.util.storage.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    private final String ADMIN_AVATAR_DIR = FileUtil.STATIC_FOLDER + "/img/admin/avatar";

    @Override
    public UploadFileResponse uploadAdminAvatar(MultipartFile avatarFile, String baseUrl) {
        if (avatarFile.isEmpty()) {
            throw new MyFileNotFoundException("Không tìm thấy file");
        }
        File uploadedFile = FileUtil.uploadFile(avatarFile, ADMIN_AVATAR_DIR);
        return new UploadFileResponse(
                baseUrl + "/img/admin/avatar/" + uploadedFile.getName(),
                uploadedFile.getName()
        );
    }
}
