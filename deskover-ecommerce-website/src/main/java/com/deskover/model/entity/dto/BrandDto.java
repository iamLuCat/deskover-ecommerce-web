package com.deskover.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import com.deskover.model.entity.database.Brand;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto implements Serializable {
    private static final long serialVersionUID = 1242794625438553826L;
    private Long id;
    @NotBlank(message = "Không bỏ trống tên thương hiệu")
    private String name;
    private String description;
    @NotBlank(message = "Không để trống slug")
    private String slug;
    private Boolean actived;
    private Timestamp modifiedAt;
    private String modifiedBy;

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.description = brand.getDescription();
        this.slug = brand.getSlug();
        this.actived = brand.getActived();
        this.modifiedAt = brand.getModifiedAt();
        this.modifiedBy = brand.getModifiedBy();
    }
}
