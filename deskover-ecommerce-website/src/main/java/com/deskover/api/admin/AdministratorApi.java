package com.deskover.api.admin;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.dto.AdministratorDto;
import com.deskover.service.AdminService;

@RestController
@RequestMapping("v1/api/admin/administrator")
public class AdministratorApi {
    @Autowired
    AdminService adminService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> doGetProfile(@PathVariable("id") Long id) {
        AdministratorDto admin = mapper.map(adminService.getById(id), AdministratorDto.class);
        if (admin == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Administrator Not Found"));
        }
        return ResponseEntity.ok(admin);
    }

}
