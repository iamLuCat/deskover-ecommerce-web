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
@Table(name = "admin_role")
public class AdminRole implements Serializable {
    private static final long serialVersionUID = -6196304595113010812L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<Administrator> administrators = new LinkedHashSet<>();

}