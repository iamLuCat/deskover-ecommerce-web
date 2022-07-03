package com.deskover.dto;

import com.deskover.entity.Subcategory;
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
    private Timestamp modifiedAt;
    private Boolean actived;
    private Long categoryId;

    public SubcategoryDto(Subcategory subcategory) {
        this.id = subcategory.getId();
        this.name = subcategory.getName();
        this.description = subcategory.getDescription();
        this.slug = subcategory.getSlug();
        this.modifiedAt = subcategory.getModifiedAt();
        this.actived = subcategory.getActived();
        this.categoryId = subcategory.getCategory().getId();
    }
}
