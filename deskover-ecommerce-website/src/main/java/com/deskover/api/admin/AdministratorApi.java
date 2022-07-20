package com.deskover.api.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdminUpdatePassDto;
import com.deskover.dto.AdministratorDto;
import com.deskover.entity.Administrator;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminService;
import com.deskover.util.ValidationUtil;

@RestController
@RequestMapping("v1/api/admin")
public class AdministratorApi {
	@Autowired
	AdminService adminService;

	@Autowired
	AdminAuthorityService adminAuthorityService;

	@GetMapping("/administrator")
	public ResponseEntity<?> doGetIsActived(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, @RequestParam("isActive") Optional<Boolean> isActive) {
		Page<Administrator> Admins = adminService.getByActived(isActive.orElse(Boolean.TRUE), page.orElse(0),
				size.orElse(1));
		if (Admins.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Not Found Category Activated"));
		}
		return ResponseEntity.ok(Admins);
	}

	@GetMapping("/administrator/actived")
    public ResponseEntity<?> doGetAllActive() {
        List<Administrator> admins = adminService.getByActived(Boolean.TRUE);
        if (admins.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Not Found Category Activated"));
        }
        return ResponseEntity.ok(admins);
    }
	
	@PostMapping("/administrator/datatables")
	public ResponseEntity<?> doGetForDatatablesByActive(@Valid @RequestBody DataTablesInput input,
			@RequestParam("isActive") Optional<Boolean> isActive) {
		return ResponseEntity.ok(adminService.getByActiveForDatatables(input, isActive.orElse(Boolean.TRUE)));
	}

	@GetMapping("/administrator/{id}")
	public ResponseEntity<?> doGetProfile(@PathVariable("id") Long id) {
		try {
			Administrator admin = adminService.getById(id);
			if (admin == null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Administrator not found"));
			}
			return ResponseEntity.ok(admin);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
		}
		
	
	}

	@PostMapping("/administrator")
	public ResponseEntity<?> doCreate(@Valid @RequestBody AdminCreateDto admin, BindingResult result) {
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			AdministratorDto adminCreated = adminService.create(admin);
			return ResponseEntity.ok().body(adminCreated);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/administrator")
	public ResponseEntity<?> doUpdate(@Valid @RequestBody AdministratorDto admin, BindingResult result) {
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			AdministratorDto adminUpdated = adminService.update(admin);
			return ResponseEntity.ok().body(adminUpdated);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/administrator/password")
	public ResponseEntity<?> doUpdatePassword(@Valid @RequestBody AdminUpdatePassDto admin, BindingResult result) {
		if (result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			AdministratorDto adminUpdated = adminService.updatePassword(admin);
			return ResponseEntity.ok().body(adminUpdated);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage(),e);
		}
	}

	@DeleteMapping("/administrator/{id}")
	public ResponseEntity<?> doChangeActive(@PathVariable("id") Long id) {
		try {
			adminService.changeActived(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping("/administrator/authority")
	public ResponseEntity<?> doChangeRole(@RequestParam(value = "adminId", required = true) Long adminId,
			@RequestParam(value = "roleId", required = true) Long roleId) {
		try {
			adminAuthorityService.changeRole(adminId, roleId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

}
