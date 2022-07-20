package com.deskover.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discount")
public class Discount implements Serializable {
    private static final long serialVersionUID = 7245682534343526382L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    @Column(name = "percent", nullable = false)
    private Integer percent;

    @Column(name = "start_date", nullable = false)
    @CreationTimestamp
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;
    
//    @NotBlank(message = "Không bỏ trống kích hoạt")
    @Column(name = "actived")
    private Boolean actived;

    @JsonIgnore
    @OneToMany(mappedBy = "discount")
    private Set<Product> products = new LinkedHashSet<>();

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

}