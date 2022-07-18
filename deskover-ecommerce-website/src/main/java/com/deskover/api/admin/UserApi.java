package com.deskover.api.admin;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/api/admin")
public class UserApi {
	@Autowired
	UserService service;

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> changeActive(@PathVariable("id") Long id) {
		try {
			service.changeActived(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PostMapping("/user/datatables")
	public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input,
			@RequestParam("isActive") Optional<Boolean> isActive) {
		return ResponseEntity.ok(service.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
	}
}
