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
public class SubcategoryDto implements Serializable {
    private static final long serialVersionUID = 4972006607319969625L;
    private Long id;
    private String name;
    private String description;
    private String slug;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Boolean actived;
}
