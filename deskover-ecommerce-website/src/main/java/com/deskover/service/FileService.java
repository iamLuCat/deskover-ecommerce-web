package com.deskover.service;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	//used to upload a file
	String uploadFile(File file, String fileName) throws IOException;
	//used to convert MultipartFile to File
	File convertToFile(MultipartFile multipartFile, String fileName) throws IOException;
	//used to get extension of a uploaded file
	String getExtension(String fileName);
}