package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "admin")
    private AdminPassword adminPasswords;

    @Column(name = "avatar", length = 128)
    private String avatar;

}