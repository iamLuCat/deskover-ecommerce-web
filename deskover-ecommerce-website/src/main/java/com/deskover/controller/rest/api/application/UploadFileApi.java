package com.deskover.controller.rest.api.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/customer")
public class UploadFileApi {
	
	 @Autowired
	 private UserService userService;
	 
	 @PostMapping("/upload-file")
	    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	        try {
	             userService.uploadFile(file);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }
}
