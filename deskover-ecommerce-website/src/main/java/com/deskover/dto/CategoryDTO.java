package com.deskover.dto;

import com.deskover.entity.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    private String slug;
    private Boolean enabled;

    public void toEntity(Category category) {
        category.setId(this.id);
        category.setName(this.name);
        category.setSlug(this.slug);
        category.setEnabled(this.enabled);
    }
}
