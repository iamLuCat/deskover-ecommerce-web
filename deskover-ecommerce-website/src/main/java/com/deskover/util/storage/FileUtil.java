package com.deskover.util.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

public class FileUtil {
    public static String STATIC_FOLDER = "src/main/resources/static";

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static File uploadFile(MultipartFile file, String uploadPath) {
        File uploadFolder = new File(uploadPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        File uploadedFile = new File(uploadFolder.getAbsolutePath() + "/" + file.getOriginalFilename());
        System.out.println(uploadFolder.getAbsolutePath());
        System.out.println(uploadedFile.getAbsolutePath());
        try {
            file.transferTo(uploadedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedFile;
    }
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public File changeFileName(File file, String newFileName) {
        String filePath = file.getAbsolutePath();
        String fileName = file.getName();
        String fileExtension = getExtension(fileName);
        String newFilePath = filePath.replace(fileName, newFileName + "." + fileExtension);
        File newFile = new File(newFilePath);
        file.renameTo(newFile);
        return newFile;
    }

}
