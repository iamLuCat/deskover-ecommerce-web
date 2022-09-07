package com.deskover.configuration.jwt;

import com.deskover.configuration.userdetail.AdminDetailsService;
import com.deskover.dto.MessageResponse;
import com.deskover.dto.login.JwtRequest;
import com.deskover.dto.login.JwtResponse;
import com.deskover.entity.Administrator;
import com.deskover.service.AdminService;
import com.deskover.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/api/admin/auth")
public class DashboardAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AdminDetailsService adminDetailsService;

	@Autowired
	private AdminService adminService;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (DisabledException e) {
			return  ResponseEntity.badRequest().body(new MessageResponse("Tài khoản đã bị khoá")) ;
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản hoặc mật khẩu không đúng")) ;
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Lỗi hệ thống")) ;
		}

		final UserDetails userDetails = adminDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		Administrator adminProfile = adminService.getPrincipal(userDetails.getUsername());
		adminService.updateLastLogin(userDetails.getUsername());

		/*return ResponseEntity.ok(new JwtResponse(token, adminProfile.getFullname(),adminProfile.getAvatar(),adminProfile.getAuthorities()));*/
		return ResponseEntity.ok(new JwtResponse(token, adminProfile.getFullname(),adminProfile.getAvatar(),adminProfile.getAuthority()));
	}
	
		
	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
	@GetMapping("/get-principal")
    public ResponseEntity<?> getProfile() {
		return ResponseEntity.ok(adminService.getPrincipal());
    }

	@GetMapping("/logout")
	public ResponseEntity<?> logout() {
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok(new MessageResponse("Đăng xuất thành công"));
	}
}
