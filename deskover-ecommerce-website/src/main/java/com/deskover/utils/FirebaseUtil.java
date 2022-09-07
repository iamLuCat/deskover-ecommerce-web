package com.deskover.utils;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FirebaseUtil {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/deskover-web-37ce6.appspot.com/o/%s?alt=media";
    private static final String JSON_FILE_NAME = "src/main/resources/credentials/deskover-firebase.json";

    public static String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("deskover-web-37ce6.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image").build();

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(JSON_FILE_NAME));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    public static File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
