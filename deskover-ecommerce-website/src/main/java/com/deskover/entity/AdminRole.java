package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admin_role")
public class AdminRole implements Serializable {
    private static final long serialVersionUID = -6196304595113010812L;
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

}