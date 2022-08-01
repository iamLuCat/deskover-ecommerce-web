package com.deskover.model.entity.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = -8404411530640628703L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotBlank(message="Không bỏ trống tên")
    @Column(name = "name")
    private String name;
    
    @Column(name = "img")
    private String img;
    
    @Column(name = "description", length = 150)
    private String description;

    @NotBlank(message="Không bỏ trống slug")
    @Column(name = "slug", nullable = false, length = 50)
    private String slug;

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "actived")
    private Boolean actived;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Subcategory> subcategories = new LinkedHashSet<>();

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

}