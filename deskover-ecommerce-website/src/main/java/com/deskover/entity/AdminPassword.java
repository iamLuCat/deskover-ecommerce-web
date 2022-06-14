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
@Table(name = "admin_password")
public class AdminPassword implements Serializable {
    private static final long serialVersionUID = -2576114564567147443L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Administrator admin;

}