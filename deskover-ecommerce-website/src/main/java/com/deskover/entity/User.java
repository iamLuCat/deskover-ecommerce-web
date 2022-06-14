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
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 7633240970764489447L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "fullname", nullable = false, length = 128)
    private String fullname;

    @Column(name = "avatar", length = 128)
    private String avatar;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    @Column(name = "last_login", nullable = false)
    private Instant lastLogin;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @Column(name = "verify", nullable = false)
    private Boolean verify = false;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Contact> contacts = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserPassword> userPasswords = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Cart> carts = new LinkedHashSet<>();

}