package com.deskover.configuration.security.jwt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deskover.configuration.security.jwt.JwtUserDetailsService;
import com.deskover.configuration.security.jwt.entity.JwtRequest;
import com.deskover.configuration.security.jwt.entity.JwtResponse;
import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.util.JwtTokenUtil;

@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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

		// } catch (DisabledException ex) {
		// 	throw new ResponseStatusException(HttpStatus.LOCKED,"Tài khoản đã bị khóa hoặc chưa được kích hoạt", ex);
		// } catch (BadCredentialsException ex) {
		// 	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tên tài khoản hoặc mật khẩu không đúng", ex);
		// } catch (Exception ex) {
		// 	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Lỗi không xác định",ex);

		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
	@GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
