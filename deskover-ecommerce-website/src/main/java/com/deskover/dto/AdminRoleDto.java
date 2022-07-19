package com.deskover.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleDto implements Serializable {
    private static final long serialVersionUID = 1635265395422077121L;
    private Long id;
    private String name;
    private String description;
    private Timestamp modifiedAt;
}
