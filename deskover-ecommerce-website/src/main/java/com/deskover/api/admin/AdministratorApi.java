package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdministratorDto;
import com.deskover.service.AdminAuthorityService;
import com.deskover.service.AdminPasswordService;
import com.deskover.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/admin/administrator")
public class AdministratorApi {
    @Autowired
    AdminService adminService;

    @Autowired
    AdminPasswordService adminPasswordService;

    @Autowired
    AdminAuthorityService adminAuthorityService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> doGetProfile(@PathVariable("id") Long id){
        AdministratorDto admin = mapper.map(adminService.getById(id),AdministratorDto.class);
        if(admin == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Administrator not found"));
        }
        return ResponseEntity.ok(admin);
    }

    @PostMapping("")
    public ResponseEntity<?> doCreate(@RequestBody AdminCreateDto admin){
        try {
            adminService.create(admin);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> doGetProfile(@PathVariable("id") Long id) {
//        AdministratorDto admin = mapper.map(adminService.getById(id), AdministratorDto.class);
//        if (admin == null) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Administrator Not Found"));
//        }
//        admin.setPassword(mapper.map(adminPasswordService.getById(admin.getId()),AdminPasswordDto.class));
//        admin.setAuthorities(adminAuthorityService.getByAdminId(admin.getId()));
//        return ResponseEntity.ok(admin);
//    }

}
