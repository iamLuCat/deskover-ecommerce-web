package com.deskover.api.admin;

import com.deskover.configuration.security.payload.response.MessageResponse;
import com.deskover.entity.Administrator;
import com.deskover.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/admin")
public class AdministratorApi {
    @Autowired
    AdminService adminService;

    @GetMapping("/{id}")
    public ResponseEntity<?> doGetProfile(@PathVariable("id") Long id) {
        Administrator admin = adminService.getById(id);
        if (admin == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Administrator Not Found"));
        }
        return ResponseEntity.ok(admin);
    }
}
