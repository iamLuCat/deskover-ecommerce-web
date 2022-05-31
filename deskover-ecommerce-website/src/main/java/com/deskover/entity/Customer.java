package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = -1334503181688676060L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "fullname", nullable = false, length = 128)
    private String fullname;

    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "photo", length = 128)
    private String photo;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @Column(name = "verify", nullable = false)
    private Boolean verify = false;

}