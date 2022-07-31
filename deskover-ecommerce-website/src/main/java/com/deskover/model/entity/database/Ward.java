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
@Table(name = "ward")
public class Ward implements Serializable {
    private static final long serialVersionUID = 1345797089049024184L;
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "_name", nullable = false, length = 50)
    private String name;

    @Column(name = "_prefix", length = 20)
    private String prefix;

    @Column(name = "_province_id")
    private Long provinceId;

    @Column(name = "_district_id")
    private Long districtId;

}