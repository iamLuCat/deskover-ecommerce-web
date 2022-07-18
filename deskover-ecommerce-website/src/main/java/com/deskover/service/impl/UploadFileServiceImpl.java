package com.deskover.service.impl;

import com.deskover.constant.FileConstant;
import com.deskover.dto.UploadFile;
import com.deskover.service.UploadFileService;
import com.deskover.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Override
    public UploadFile uploadAdminAvatar(MultipartFile file) {
        return uploadFile(file, FileConstant.AVATAR_ADMIN_FOLDER);
    }

    @Override
    public UploadFile uploadImageProduct(MultipartFile file) {
        return uploadFile(file, FileConstant.TEMP_PATH);
    }

    private UploadFile uploadFile(MultipartFile file, String folderPath) {
        FileUtil.uploadFile(file, FileConstant.STATIC_PATH +  folderPath);
        String fileName = file.getOriginalFilename();
        String fileUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/").path(folderPath).path(fileName)
                .build()
                .toUriString();
        return new UploadFile(fileUrl, fileName);
    }
}
