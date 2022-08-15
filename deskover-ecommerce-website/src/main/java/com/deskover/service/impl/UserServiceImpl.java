package com.deskover.service.impl;

import com.deskover.model.entity.database.UserPassword;
import com.deskover.model.entity.database.Users;
import com.deskover.model.entity.database.repository.UserRepository;
import com.deskover.model.entity.database.repository.datatable.UserRepoForDatatables;
import com.deskover.model.entity.dto.ChangePasswordDto;
import com.deskover.model.entity.dto.UploadFile;
import com.deskover.model.entity.dto.UserCreateDto;
import com.deskover.other.constant.PathConstant;
import com.deskover.other.util.OrderNumberUtil;
import com.deskover.service.UploadFileService;
import com.deskover.service.UserPasswordService;
import com.deskover.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private UserRepoForDatatables repoForDatatables;
	
	@Autowired
	private UserPasswordService userPasswordService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired OrderNumberUtil number;

	@Override
	@Transactional
	public void changeActived(Long id) {
		Users user = repo.findById(id).orElse(null);
		if(user == null) {
			throw new IllegalArgumentException("Không tìm thấy user");
		}
		user.setActived(!user.getActived());
		user.setModifiedAt(new Timestamp(System.currentTimeMillis()));
		user.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		repo.saveAndFlush(user);
	}

	@Override
	public DataTablesOutput<Users> getByActiveForDatatables(DataTablesInput input, Boolean isActive) {
		DataTablesOutput<Users> Users = repoForDatatables.findAll(input,
				(root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actived"), isActive));
		if (Users.getError() != null) {
			throw new IllegalArgumentException(Users.getError());
		}
		return Users;
	}

	@Override
	public Users findById(Long id) {
		return repo.getById(id);
	}

	@Override
	public Users findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public Users create(UserCreateDto userRequest) {
		if(repo.existsByUsername(userRequest.getUsername())) {
			throw new IllegalArgumentException("Username này đã tồn tại vui lòng nhập username khác");
		}
		if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
			throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
		}else {
			Users createUser = new Users();
			createUser.setUsername(userRequest.getUsername());
			createUser.setFullname(userRequest.getFullname());
			createUser.setAvatar(null);
			createUser.setLastLogin(null);
			createUser.setActived(Boolean.FALSE);
			createUser.setVerify(Boolean.FALSE);
			createUser.setModifiedAt(new Timestamp(System.currentTimeMillis()));
			createUser.setModifiedBy(null);
			Users createdUser = repo.save(createUser);
			
			userPasswordService.create(createdUser, userRequest.getConfirmPassword());
			
			return createdUser;
		}
	}
	
	@Override
	public Users create1(UserCreateDto userRequest){
		if(repo.existsByUsername(userRequest.getUsername())) {
			throw new IllegalArgumentException("Username này đã tồn tại vui lòng nhập username khác");
		}
		if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
			throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
		}else {
			Users createUser = new Users();
			createUser.setUsername(userRequest.getUsername());
			createUser.setFullname(userRequest.getFullname());
			createUser.setEmail(userRequest.getEmail());
			createUser.setPhone(Integer.toString(number.gernerateNumber()) );
			createUser.setAvatar(null);
			createUser.setLastLogin(null);
			createUser.setActived(Boolean.FALSE);
			createUser.setVerify(Boolean.FALSE);
			createUser.setModifiedAt(new Timestamp(System.currentTimeMillis()));
			createUser.setModifiedBy("haipv");
			Users createdUser = repo.save(createUser);
			userPasswordService.create(createdUser, userRequest.getConfirmPassword());
			return createdUser;
		}
	}

	@Override
	public void updatePassword(ChangePasswordDto userRequest) {
		userPasswordService.updatePassword(SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getName(), userRequest);
	}

	@Override
	public Users uploadFile(MultipartFile file) {
		 UploadFile uploadFile = uploadFileService.uploadFileToFolder(file, PathConstant.IMAGE_USER);
		 Users users = this.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		 users.setAvatar(uploadFile.getFilename());
		 return  repo.saveAndFlush(users);
	}

	@Override
	@Transactional
	public Users update(Users user) {
		return repo.saveAndFlush(user);
	}

	@Override
	public Users findUser(String username) {
		return repo.findByUsernameOrEmailOrPhone(username, username, username);
	}

	@Override
	public void updateTimestamp(Users user) {
		user.setLastLogin(new Timestamp(System.currentTimeMillis()));
		repo.save(user);		
	}

	@Override
	public Long totalCustomers() {
		return repo.count();
	}

	@Override
	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("email");
		Users users = repo.findByEmail(email);
		UserPassword password;
		
		if(Objects.isNull(users)) {
			Users createUser = new Users();
			String name = oauth2.getPrincipal().getAttribute("name");
			createUser.setUsername(email);
			createUser.setFullname(name);
			createUser.setEmail(email);
			createUser.setAvatar(null);
			createUser.setLastLogin(new Timestamp(System.currentTimeMillis()));
			createUser.setPhone(Integer.toString(number.gernerateNumber()));
			createUser.setActived(Boolean.FALSE);
			createUser.setVerify(Boolean.TRUE);
			createUser.setModifiedAt(new Timestamp(System.currentTimeMillis()));
			createUser.setModifiedBy(null);
			users = repo.save(createUser);
			password = userPasswordService.create(users, Long.toHexString(System.currentTimeMillis()));
		} else {
			password = userPasswordService.getPasswordByUsername(users.getUsername());
		}
		
		UserDetails userDetail = new User(
				users.getUsername(),
				password.getPassword(),
				users.getActived(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}

}
