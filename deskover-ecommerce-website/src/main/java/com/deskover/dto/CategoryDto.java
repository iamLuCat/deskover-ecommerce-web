package com.deskover.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 4488429993814474886L;
    private Long id;
    private String name;
    private String description;
    private String slug;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Boolean actived;
    private Set<SubcategoryDto> subcategories;
}
