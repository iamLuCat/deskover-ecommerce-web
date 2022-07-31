package com.deskover.model.entity.database;

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
@Table(name = "province")
public class Province implements Serializable {
    private static final long serialVersionUID = 7746926219190939730L;
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "_name", length = 50)
    private String name;

    @Column(name = "_code", length = 20)
    private String code;

}