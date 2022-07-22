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
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "persistent_logins")
public class PersistentLogin implements Serializable {
    private static final long serialVersionUID = -405402035509434108L;
    @Id
    @Column(name = "series", nullable = false, length = 64)
    private String id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "token", nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Timestamp lastUsed;

}