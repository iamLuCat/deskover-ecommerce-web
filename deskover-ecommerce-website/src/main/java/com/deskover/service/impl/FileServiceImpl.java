package com.deskover.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.service.FileService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FileServiceImpl implements FileService {

	private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/deskover-web-37ce6.appspot.com/o/%s?alt=media";
	private static final String JSON_FILE_NAME = "src/main/resources/credentials/deskover-firebase.json";
	@Override
	public String uploadFile(File file, String fileName) throws IOException {
		// TODO Auto-generated method stub
		
        BlobId blobId = BlobId.of("deskover-web-37ce6.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image").build();
        
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(JSON_FILE_NAME));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
	}

	@Override
	public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		// TODO Auto-generated method stub
		File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
	}

	@Override
	public String getExtension(String fileName) {
		// TODO Auto-generated method stub
		 return fileName.substring(fileName.lastIndexOf("."));
	}

}