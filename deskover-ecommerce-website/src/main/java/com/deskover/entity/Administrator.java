package com.deskover.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "administrator")
public class Administrator implements Serializable {
    private static final long serialVersionUID = -9036502519709796374L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "fullname", nullable = false, length = 128)
    private String fullname;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @Column(name = "avatar", length = 128)
    private String avatar;

    @Column(name = "modified_user", length = 50)
    private String modifiedUser;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "admin")
    private AdminPassword password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "admin")
    private Set<AdminAuthority> authorities = new LinkedHashSet<>();

}