package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.ghtk.UrlGGStrogeResponDto;
import com.deskover.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.ghtk.UrlGGStrogeResponDto;
import com.deskover.service.FileService;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class UploadFiledGGStorageApi {

	@PostMapping("/upload-file")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile files) {
		try {
			String fileName = files.getOriginalFilename();
			
			File file = FileUtil.convertToFile(files, fileName);
			String TEMP_URL = FileUtil.uploadFile(file, fileName);
			UrlGGStrogeResponDto url = new UrlGGStrogeResponDto();
			url.setUrl(TEMP_URL);
			if (url.getUrl() == null) {
				file.delete();
				return ResponseEntity.badRequest().body(new MessageResponse("Upload thất bại"));
			} else {
				url.setMessage("Upload thành công");
				file.delete();
				return ResponseEntity.ok(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/upload-files")
	public ResponseEntity<?> upload(@RequestParam("files") MultipartFile[] files) {
		try {
			List<UrlGGStrogeResponDto> response = new ArrayList<>();
			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename();
				File file = FileUtil.convertToFile(multipartFile, fileName);
				String TEMP_URL = FileUtil.uploadFile(file, fileName);
				UrlGGStrogeResponDto url = new UrlGGStrogeResponDto();
				url.setUrl(TEMP_URL);
				if (url.getUrl() == null) {
					url.setMessage("Upload thất bại");
					response.add(url);
					file.delete();
				} else {
					url.setMessage("Upload thành công");
					response.add(url);
					file.delete();
				}
			}

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
