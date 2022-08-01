package com.deskover.model.entity.database;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class Users implements Serializable {
    private static final long serialVersionUID = 7633240970764489447L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "fullname", nullable = false, length = 128)
    private String fullname;
    
    @Email(message = "Email không đúng định dạng")
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar", length = 128)
    private String avatar;

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @Column(name = "verify", nullable = false)
    private Boolean verify = false;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserAddress> contacts = new LinkedHashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private UserPassword userPassword = new UserPassword();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Cart> carts = new LinkedHashSet<>();

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

}