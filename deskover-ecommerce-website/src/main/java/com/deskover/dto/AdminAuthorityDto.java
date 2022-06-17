package com.deskover.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuthorityDto implements Serializable {
    private static final long serialVersionUID = 1703450084784021528L;
    private Long id;
    private AdminRoleDto role;
}
