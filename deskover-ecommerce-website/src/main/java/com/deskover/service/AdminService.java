package com.deskover.service;

import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdministratorDto;
import com.deskover.entity.Administrator;

public interface AdminService {
    Administrator getById(Long id);
    Administrator getByUsername(String username);
    AdministratorDto getPrincipal();
    AdministratorDto create(AdminCreateDto adminRequest);
    AdministratorDto update(AdministratorDto adminUpdate);
    void delete(Long id);
    Boolean existsUsername(String username);
    Boolean existsUsername(AdministratorDto adminUpdate);
}
