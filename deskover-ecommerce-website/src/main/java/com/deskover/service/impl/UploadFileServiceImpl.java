package com.deskover.service.impl;

import com.deskover.constant.PathConstant;
import com.deskover.dto.UploadFile;
import com.deskover.service.UploadFileService;
import com.deskover.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Override
    public UploadFile uploadFileToTempFolder(MultipartFile file) {
        return uploadFile(file, PathConstant.TEMP);
    }

    private UploadFile uploadFile(MultipartFile file, String folderPath) {
        FileUtil.uploadFile(file, PathConstant.STATIC +  folderPath);
        String fileName = file.getOriginalFilename();
        String fileUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/").path(folderPath).path(fileName)
                .build()
                .toUriString();
        return new UploadFile(fileUrl, fileName);
    }
}
