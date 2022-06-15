package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Column(name = "modified_date", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedDate;

    @Column(name = "deleted_date")
    @CreationTimestamp
    private Timestamp deletedDate;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToMany(mappedBy = "product")
    private Set<Inventory> inventories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts = new LinkedHashSet<>();

}