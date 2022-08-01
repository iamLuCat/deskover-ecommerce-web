package com.deskover.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deskover.model.entity.dto.UploadFile;
import com.deskover.other.constant.PathConstant;
import com.deskover.other.util.FileUtil;
import com.deskover.other.util.FirebaseUtil;
import com.deskover.service.UploadFileService;
import com.deskover.service.UserService;

@Service
public class UploadFileServiceImpl implements UploadFileService {
	
	@Autowired
	private UserService userService;

    @Override
    public UploadFile uploadFileToTempFolder(MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return uploadFile(file, PathConstant.TEMP);
    }

    @Override
    public void removeTempFolder() {
        FileUtil.removeFolder(PathConstant.TEMP_STATIC);
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
    
    private UploadFile uploadFileUser(MultipartFile file, String folderPath) {
        FileUtil.uploadFileUser(file, PathConstant.STATIC +  folderPath);
        String fileName = file.getOriginalFilename();
        String fileUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/").path(folderPath).path(fileName)
                .build()
                .toUriString();
        return new UploadFile(fileUrl, fileName);
    }

	@Override
	@Transactional
	public UploadFile uploadFileToFolder(MultipartFile file, String folderPath) {
//		fileName = SecurityContextHolder.getContext().getAuthentication().getName().concat(FirebaseUtil.getExtension(fileName));
		return this.uploadFileUser(file, folderPath);
	}
}
