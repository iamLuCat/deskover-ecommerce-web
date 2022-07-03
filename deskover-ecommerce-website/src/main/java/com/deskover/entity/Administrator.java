package com.deskover.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Không được bỏ trống username")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Không được bỏ trống fullname")
    @Column(name = "fullname", nullable = false, length = 128)
    private String fullname;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @Column(name = "avatar", length = 128)
    private String avatar;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "admin")
    private AdminPassword password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "admin")
    private Set<AdminAuthority> authorities = new LinkedHashSet<>();

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;
}