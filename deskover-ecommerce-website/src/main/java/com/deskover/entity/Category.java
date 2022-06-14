package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.Instant;
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

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "slug", nullable = false, length = 50)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "actived")
    private Boolean actived;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Subcategory> subcategories = new LinkedHashSet<>();

}