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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admin_authority")
public class AdminAuthority implements Serializable {
    private static final long serialVersionUID = 8233794620734552409L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private AdminRole role;

    /*@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Administrator admin;*/
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Administrator admin;

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

}