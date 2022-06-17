package com.deskover.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorDto implements Serializable {
    private static final long serialVersionUID = 1826221885734209898L;
    private Long id;
    private String username;
    private String fullname;
    private Timestamp lastLogin;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Boolean actived;
    private String avatar;
    private AdminPasswordDto password;
    private Set<AdminAuthorityDto> authorities;
}
