package com.deskover.entity;

import com.deskover.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = 6741406216094324329L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "slug", nullable = false, length = 50)
    private String slug;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    public void toDTO (CategoryDTO categoryDTO) {
        categoryDTO.setId(this.id);
        categoryDTO.setName(this.name);
        categoryDTO.setSlug(this.slug);
        categoryDTO.setEnabled(this.enabled);
    }

}