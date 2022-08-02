package com.deskover.other.util;

import org.apache.commons.io.FileUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Date;

public class FileUtil {
    public static String getExtension(Path path) {
        String filename = path.getFileName().toString();
        return "." + filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static String getExtension(String filename) {
        return "." + filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static String getNameAddTime(String filename) {
        return filename + "_" + new Timestamp(System.currentTimeMillis()) + getExtension(filename);
    }

    public static String changeNameAddTime(String newName, String oldName) {
        return newName + "_" + new Date().getTime() + getExtension(oldName);
    }

    public static String changeName(String newName, String oldName) {
        return newName + getExtension(oldName);
    }

    public static void removeFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File copyFile(String source, String target) {
        File sourceFile = FileUtils.getFile(source);
        File targetFile = FileUtils.getFile(target + getExtension(source));
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            }
            if (!sourceFile.isDirectory()) {
                FileUtils.copyFile(sourceFile, targetFile);
            }
            return targetFile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void uploadFile(MultipartFile file, String folderPath) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File uploadedFile = new File(dir.getAbsolutePath(), file.getOriginalFilename());
        try {
            file.transferTo(uploadedFile);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    public static String uploadFileUser(MultipartFile file, String folderPath) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String fileName = file.getOriginalFilename();
        fileName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        File uploadedFile = new File(dir.getAbsolutePath(),fileName.concat(FirebaseUtil.getExtension(file.getOriginalFilename())));
        try {
            file.transferTo(uploadedFile);
            return uploadedFile.getName();
            
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}