package com.deskover.dto;

import com.deskover.entity.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateDto implements Serializable {
    private String username;
    private String fullname;
    private String password;
    private List<AdminRole> listRole;
}
