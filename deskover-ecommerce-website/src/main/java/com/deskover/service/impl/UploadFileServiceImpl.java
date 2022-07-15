package com.deskover.service.impl;

import com.deskover.service.UploadFileService;
import com.deskover.util.storage.UploadFileResponse;
import com.deskover.util.storage.exception.MyFileNotFoundException;
import com.deskover.util.storage.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    private final String AVATAR_ADMIN_FOLDER = "/img/admin/avatars/";
    private final String PRODUCT_IMAGE_FOLDER = "/img/shop/products/";

    @Override
    public UploadFileResponse uploadAdminAvatar(MultipartFile avatarFile, String baseUrl) {
        return uploadFile(avatarFile, AVATAR_ADMIN_FOLDER, baseUrl);
    }

    @Override
    public UploadFileResponse uploadImageProduct(MultipartFile avatarFile, String baseUrl) {
        return uploadFile(avatarFile, PRODUCT_IMAGE_FOLDER, baseUrl);
    }

    private UploadFileResponse uploadFile(MultipartFile avatarFile, String path, String baseUrl) {
        if (avatarFile.isEmpty()) {
            throw new MyFileNotFoundException("Không tìm thấy file");
        }
        File uploadedFile = FileUtil.uploadFile(avatarFile, FileUtil.STATIC_FOLDER_DIR + path);
        return new UploadFileResponse(
                baseUrl + path + avatarFile.getOriginalFilename(),
                uploadedFile.getName()
        );
    }
}
