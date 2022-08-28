package com.deskover.service.impl;

import com.deskover.model.entity.database.AdminAuthority;
import com.deskover.model.entity.database.AdminRole;
import com.deskover.model.entity.database.Administrator;
import com.deskover.model.entity.database.repository.AdminAuthorityReponsitory;
import com.deskover.model.entity.database.repository.AdminRoleRepository;
import com.deskover.model.entity.database.repository.AdministratorRepository;
import com.deskover.model.entity.database.repository.datatable.AdminRepoForDatatables;
import com.deskover.model.entity.dto.AdminCreateDto;
import com.deskover.model.entity.dto.AdministratorDto;
import com.deskover.model.entity.dto.ChangePasswordDto;
import com.deskover.other.constant.PathConstant;
import com.deskover.other.util.FileUtil;
import com.deskover.other.util.MapperUtil;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminRoleService;
import com.deskover.service.AdminService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdministratorRepository repo;

    @Autowired
    private AdminAuthorityReponsitory authorityRepo;

    @Autowired
    private AdminRoleRepository roleRepo;

    @Autowired
    private AdminAuthorityService adminAuthorityService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AdminRepoForDatatables repoForDatatables;

    @Override
    public List<AdminRole> getAllRole() {
        return roleRepo.findAll();
    }

    @Override
    public Administrator getById(Long id) {
        Administrator admin = repo.findById(id).orElse(null);
        if (admin == null) {
            throw new IllegalArgumentException("Không tìm thấy user: " + id);
        }
        /*admin.setAuthorities(adminAuthorityService.getByAdminId(admin.getId()));*/
        return admin;
    }

    @Override
    public Administrator getByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public Administrator getPrincipal() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findByUsername(username);
    }

    @Override
    public Administrator getPrincipal(String username) {
        return repo.findByUsername(username);
    }

    @Override
    @Transactional
    public AdministratorDto create(AdminCreateDto adminRequest) {
        if (repo.existsByUsername(adminRequest.getUsername())) {
            throw new IllegalArgumentException("Username này đã tồn tại");
        }

        Administrator entityAdmin = MapperUtil.map(adminRequest, Administrator.class);
        String hashPass = passwordEncoder.encode(entityAdmin.getPassword());
        entityAdmin.setPassword(hashPass);
        entityAdmin.setActived(Boolean.TRUE);
        entityAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        entityAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Administrator adminCreated = repo.save(entityAdmin);

        AdminAuthority defaultAuthority = new AdminAuthority();
        defaultAuthority.setAdmin(adminCreated);
        defaultAuthority.setRole(adminRoleService.getByRoleId("ROLE_STAFF"));
        AdminAuthority authorityDefault = adminAuthorityService.create(defaultAuthority);
        /*Set<AdminAuthority> authorities = new LinkedHashSet<AdminAuthority>();
        authorities.add(authorityDefault);
        adminCreated.setAuthority(authorities);*/
        adminCreated.setAuthority(authorityDefault);

        return MapperUtil.map(entityAdmin, AdministratorDto.class);
    }

    @Override
    @Transactional
    public AdministratorDto update(AdministratorDto adminUpdate) {
        if (this.existsUsername(adminUpdate)) {
            throw new IllegalArgumentException("Username này đã tồn tại");
        }

        Administrator entityAdmin = MapperUtil.map(adminUpdate, Administrator.class);
        entityAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        entityAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        Administrator adminUpdated = repo.saveAndFlush(entityAdmin);
        return MapperUtil.map(adminUpdated, AdministratorDto.class);
    }

    @Override
    @Transactional
    public AdministratorDto updatePassword(ChangePasswordDto adminUpdatePass) {
        Administrator existsAdmin = this.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!passwordEncoder.matches(adminUpdatePass.getOldPassword(), existsAdmin.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng");
        } else {
            if (adminUpdatePass.getNewPassword().equals(adminUpdatePass.getOldPassword())) {
                throw new IllegalArgumentException("Mật khẩu mới không được trùng với mật khẩu cũ");
            } else {
                if (!adminUpdatePass.getConfirmPassword().equals(adminUpdatePass.getNewPassword())) {
                    throw new IllegalArgumentException("Mật khẩu xác nhận không khớp");
                } else {
                    String hashPass = passwordEncoder.encode(adminUpdatePass.getConfirmPassword());
                    existsAdmin.setPassword(hashPass);
                    return MapperUtil.map(repo.saveAndFlush(existsAdmin), AdministratorDto.class);
                }
            }
        }
    }

    @Override
    @Transactional
    public void changeActived(Long id) {
        Administrator currentAdmin = this.getById(id);
        if (currentAdmin == null) {
            throw new IllegalArgumentException("Tài khoản admin này không tồn tại");
        }
        if (currentAdmin.getActived()) {
            currentAdmin.setActived(Boolean.FALSE);
            currentAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            currentAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.saveAndFlush(currentAdmin);
        } else {
            currentAdmin.setActived(Boolean.TRUE);
            currentAdmin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            currentAdmin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            repo.saveAndFlush(currentAdmin);
        }
    }

    @Override
    public Boolean existsUsername(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public Boolean existsUsername(AdministratorDto adminUpdate) {
        Administrator adminExists = repo.findByUsername(adminUpdate.getUsername());
        return (adminExists.getUsername() != null && !adminExists.getId().equals(adminUpdate.getId()));
    }

    @Override
    public Page<Administrator> getByActived(Boolean isActive, Integer page, Integer size) {
        return repo.findByActived(isActive, PageRequest.of(page, size));
    }

    @Override
    public DataTablesOutput<Administrator> getAllForDatatables(DataTablesInput input) {
        DataTablesOutput<Administrator> Administrator = repoForDatatables.findAll(input);
        if (Administrator.getError() != null) {
            throw new IllegalArgumentException(Administrator.getError());
        }
        return Administrator;
    }

    @Override
    public DataTablesOutput<Administrator> getByActiveForDatatables(DataTablesInput input, Boolean isActive, Long roleId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DataTablesOutput<Administrator> administrator = repoForDatatables.findAll(input, (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (isActive != null) {
                predicates.add(cb.equal(root.get("actived"), isActive));
            }
            if (roleId != null) {
                predicates.add(cb.equal(root.get("authority").get("role").get("id"), roleId));
            }
            if (username != null) {
                predicates.add(cb.notEqual(root.get("username"), username));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
        if (administrator.getError() != null) {
            throw new IllegalArgumentException(administrator.getError());
        }
        return administrator;
    }

    @Override
    public List<Administrator> getByActived(Boolean isActive) {
        return repo.findByActived(isActive);
    }

    @Override
    public void updateLastLogin(String username) {
        Administrator admin = repo.findByUsername(username);
        admin.setLastLogin(new Timestamp(System.currentTimeMillis()));
        repo.saveAndFlush(admin);
    }

    @Override
    @Transactional
    public Administrator save(Administrator admin) {
        if (admin.getId() == null) {
            if (repo.existsByUsername(admin.getUsername())) {
                throw new IllegalArgumentException("Username này đã tồn tại");
            }
            AdminRole role = roleRepo.findByRoleId(admin.getAuthority().getRole().getRoleId());

            admin.setAuthority(null);
            Administrator newAdmin = repo.save(admin);

            AdminAuthority authority = new AdminAuthority();
            authority.setRole(role);
            authority.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            authority.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            authority.setAdmin(newAdmin);
            authorityRepo.save(authority);

            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setActived(Boolean.TRUE);
            admin.setId(newAdmin.getId());
            admin.setAuthority(authority);
        } else {
            Administrator adminExist = repo.findByUsername(admin.getUsername());
            if (repo.existsByUsername(admin.getUsername()) && !adminExist.getId().equals(admin.getId())) {
                throw new IllegalArgumentException("Username này đã tồn tại");
            }
            AdminAuthority authority = authorityRepo.findById(admin.getAuthority().getId()).get();
            authority.setRole(admin.getAuthority().getRole());
            admin.setAuthority(authority);
        }
        admin.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        admin.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        String sourcePath = PathConstant.TEMP_STATIC + admin.getAvatar();
        if (FileUtils.getFile(sourcePath).exists()) {
            String destPath = PathConstant.ADMIN_AVATAR_STATIC + admin.getUsername();
            File imageFile = FileUtil.copyFile(sourcePath, destPath);
            admin.setAvatar(imageFile.getName());
        }
        FileUtil.removeFolder(PathConstant.TEMP_STATIC);

        return repo.save(admin);
    }

    @Override
    public void changePassword(String currentPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            Administrator admin = repo.findByUsername(username);
            if (admin != null) {
                if (passwordEncoder.matches(currentPassword, admin.getPassword())) {
                    admin.setPassword(passwordEncoder.encode(newPassword));
                    repo.saveAndFlush(admin);
                } else {
                    throw new IllegalArgumentException("Mật khẩu hiện tại không đúng");
                }
            }
        }
    }

}
