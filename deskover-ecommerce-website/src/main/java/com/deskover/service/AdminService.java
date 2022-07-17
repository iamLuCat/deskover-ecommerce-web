package com.deskover.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.deskover.dto.AdminCreateDto;
import com.deskover.dto.AdminUpdatePassDto;
import com.deskover.dto.AdministratorDto;
import com.deskover.entity.Administrator;

public interface AdminService {
	
    Administrator getById(Long id);
    
    Administrator getByUsername(String username);
    
    Administrator getPrincipal(String username);
    
    AdministratorDto getPrincipal();
    
    AdministratorDto create(AdminCreateDto adminRequest);
    
    AdministratorDto update(AdministratorDto adminUpdate);
    
    AdministratorDto updatePassword(AdminUpdatePassDto adminUpdatePass);
    
    void changeActived(Long id);
    
    Boolean existsUsername(String username);
    
    Boolean existsUsername(AdministratorDto adminUpdate);
    
    List<Administrator> getByActived(Boolean isActive);
    
    Page<Administrator> getByActived(Boolean isActive, Integer page, Integer size);
    
    DataTablesOutput<Administrator> getAllForDatatables(DataTablesInput input);
    
    DataTablesOutput<Administrator> getByActiveForDatatables(DataTablesInput input, Boolean isActive);
}
