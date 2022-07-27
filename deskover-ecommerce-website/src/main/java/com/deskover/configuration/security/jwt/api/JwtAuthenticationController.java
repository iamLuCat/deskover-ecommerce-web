package com.deskover.configuration.security.jwt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.jwt.JwtUserDetailsService;
import com.deskover.configuration.security.jwt.entity.JwtRequest;
import com.deskover.configuration.security.jwt.entity.JwtResponse;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Administrator;
import com.deskover.service.AdminService;
import com.deskover.util.JwtTokenUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/api/admin/auth")
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

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

		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
			
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

		Administrator adminProfile = adminService.getPrincipal(userDetails.getUsername());
		return ResponseEntity.ok(new JwtResponse(token, adminProfile.getFullname(),adminProfile.getAvatar(),adminProfile.getAuthorities()));
	}
	
		
	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
	@GetMapping("/get-principal")
    public ResponseEntity<?> getProfile() {
	        // return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return ResponseEntity.ok(adminService.getPrincipal());
    }
}
