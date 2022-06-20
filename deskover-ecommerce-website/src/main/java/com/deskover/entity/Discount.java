package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

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
    @CreationTimestamp
    private Timestamp endDate;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "modified_date", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedDate;

    @Column(name = "deleted_date")
    @CreationTimestamp
    private Timestamp deletedDate;
    
    @Column(name = "actived")
    private Boolean actived;

    @JsonIgnore
    @OneToMany(mappedBy = "discount")
    private Set<Product> products = new LinkedHashSet<>();

}