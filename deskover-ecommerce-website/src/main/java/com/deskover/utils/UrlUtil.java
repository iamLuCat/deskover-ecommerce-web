package com.deskover.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlUtil {
    public static String getImageUrl(String filename, String folderPath) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/").path(folderPath).path(filename)
                .build()
                .toUriString();
    }
}
