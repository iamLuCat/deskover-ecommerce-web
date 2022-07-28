package com.deskover.api.app;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.payload.response.MessageErrorUtil;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.ChangePasswordDto;
import com.deskover.dto.UserCreateDto;
import com.deskover.entity.UserAddress;
import com.deskover.service.UserAddressService;
import com.deskover.service.UserService;
import com.deskover.util.ValidationUtil;

@RestController("UserApiForClient")
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
@RequestMapping("v1/api/customer")
public class UserApi {
	@Autowired
	private UserAddressService contactService;
	
	@Autowired 
	UserService userService;

	@GetMapping("/user/address")
	public ResponseEntity<?> doGetAddress() {
		try {
			return ResponseEntity.ok(contactService.findByUsername());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping("/user/address")
	public ResponseEntity<?> doPostAddress(@Valid @RequestBody UserAddress userAddress, BindingResult result ){
		if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
		try {
			contactService.doPostAddAddress(userAddress);
			return ResponseEntity.ok(new MessageResponse("Thêm mới thành công"));
		} catch (Exception e) {
            MessageResponse error = MessageErrorUtil.message("Thêm mới thất bại", e);
            return ResponseEntity.badRequest().body(error);
		}
	}
	@PutMapping("/user/address")
	public ResponseEntity<?> changeAddrees(@Valid @RequestBody UserAddress userAddress, BindingResult result ){
		if (result.hasErrors()) {
            MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
		try {
			contactService.doPutAddAddress(userAddress);
			return ResponseEntity.ok(new MessageResponse("Thêm mới thành công"));
		} catch (Exception e) {
            MessageResponse error = MessageErrorUtil.message("Thêm mới thất bại", e);
            return ResponseEntity.badRequest().body(error);
		}
	}
	
	@PutMapping("/user/address/{id}")
	public ResponseEntity<?> changeActive(@PathVariable("id") Long id) {
		try {
			contactService.changeActive(id);
			return ResponseEntity.ok(contactService.findByUsername());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PutMapping("/user/address-choose/{id}")
	public ResponseEntity<?> changeChoose(@PathVariable("id") Long id){
		try {
			contactService.changeChoose(id);
			return ResponseEntity.ok(new MessageResponse("Thay đổi thành công"));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(),e);
		}
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> create(@Valid  @RequestBody UserCreateDto userCreateDto, BindingResult result){
		if(result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			userService.create(userCreateDto);
			return ResponseEntity.ok(new MessageResponse("Thêm mới thành công"));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
		}
	}

	@PutMapping("/user/password")
	public ResponseEntity<?> updatePassword( @Valid  @RequestBody ChangePasswordDto passwordDto, BindingResult result){
		if(result.hasErrors()) {
			MessageResponse errors = ValidationUtil.ConvertValidationErrors(result);
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			userService.updatePassword(passwordDto);
			return ResponseEntity.ok(new MessageResponse("Cập nhật mật khẩu thành công"));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(),e);
		}
	}
}
