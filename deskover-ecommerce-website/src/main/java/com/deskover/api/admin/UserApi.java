package com.deskover.api.admin;

import com.deskover.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController("UserApiForAdmin")
@CrossOrigin("*")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("v1/api/admin/users/")
public class UserApi {
	@Autowired
	UserService service;

	@DeleteMapping("{id}")
	public ResponseEntity<?> changeActive(@PathVariable("id") Long id) {
		try {
			service.changeActived(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}

	@PostMapping("datatables")
	public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input,
			@RequestParam("isActive") Optional<Boolean> isActive) {
		return ResponseEntity.ok(service.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
	}
}
