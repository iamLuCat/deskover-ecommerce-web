package com.deskover.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.service.UploadFileService;
import com.deskover.dto.UploadFile;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    private final String AVATAR_ADMIN_FOLDER = "/img/admin/avatars/";
    private final String PRODUCT_IMAGE_FOLDER = "/img/shop/products/";
    public static String STATIC_DIR = "src/main/resources/static";

    @Override
    public UploadFile uploadAdminAvatar(MultipartFile file, String baseUrl) {
        return uploadFile(file, AVATAR_ADMIN_FOLDER, baseUrl);
    }

    @Override
    public UploadFile uploadImageProduct(MultipartFile file, String baseUrl) {
        return uploadFile(file, PRODUCT_IMAGE_FOLDER, baseUrl);
    }

    private UploadFile uploadFile(MultipartFile file, String folder, String baseUrl) {
        File dir = new File(STATIC_DIR + folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Path root = Paths.get(STATIC_DIR + folder);
        String filename = file.getOriginalFilename();
        try {
            Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            String uploadedUrl = baseUrl + folder + filename;
            return new UploadFile(uploadedUrl, filename);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".")) + '.';
    }
}
