package com.deskover.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = 3923053989141861733L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, length = 150)
    private String slug;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "image", length = 150)
    private String image;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategory;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;
    
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Inventory> inventories = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Cart> carts = new LinkedHashSet<>();

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;
    
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Rating> ratings = new LinkedHashSet<>();

    @Lob
    @Column(name = "spec")
    private String spec;

    @Column(name = "video")
    private String video;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<ProductThumbnail> productThumbnails = new LinkedHashSet<>();

}