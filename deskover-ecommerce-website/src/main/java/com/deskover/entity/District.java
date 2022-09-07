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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "district")
public class District implements Serializable {
    private static final long serialVersionUID = 7798556053301697686L;
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "_name", length = 100)
    private String name;

    @Column(name = "_prefix", length = 20)
    private String prefix;

    @Column(name = "_province_id")
    private Long provinceId;

}