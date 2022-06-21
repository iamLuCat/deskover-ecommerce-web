package com.deskover.api.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.ghtk.UrlGGStrogeResponDto;
import com.deskover.service.FileService;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class UploadFiledGGStorageApi {
	@Autowired
	FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@PathParam("files") MultipartFile files) {
		try {
			String fileName = files.getOriginalFilename();
//			fileName = UUID.randomUUID().toString().concat(fileService.getExtension(fileName));
			
			fileName = fileService.getExtension(fileName);

			File file = fileService.convertToFile(files, fileName);
			String TEMP_URL = fileService.uploadFile(file, fileName);
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

	@PostMapping("/upload-test")
	public ResponseEntity<?> uploadd(@PathParam("files") MultipartFile[] files) {
		try {
			List<UrlGGStrogeResponDto> response = new ArrayList<>();
			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename();
				fileName = UUID.randomUUID().toString().concat(fileService.getExtension(fileName));
				File file = fileService.convertToFile(multipartFile, fileName);
				String TEMP_URL = fileService.uploadFile(file, fileName);
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
