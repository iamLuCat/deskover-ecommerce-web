package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_address")
public class UserAddress implements Serializable {
    private static final long serialVersionUID = 568706130123470738L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "address", length = 128)
    private String address;

    @Column(name = "province", length = 128)
    private String province;

    @Column(name = "district", length = 128)
    private String district;

    @Column(name = "ward", length = 128)
    private String ward;

    @Column(name = "tel", length = 10)
    private String tel;

    @Column(name = "email", nullable = false, length = 50)
    private String email;
    
    @Column(name = "actived")
    private Boolean actived;

}