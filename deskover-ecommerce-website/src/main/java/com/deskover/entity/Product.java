package com.deskover.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

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

    @Column(name = "price_sale")
    private Double priceSale;

    @Lob
    @Column(name = "img")
    private String img;

    @Lob
    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Lob
    @Column(name = "spec")
    private String spec;

    @Lob
    @Column(name = "utility")
    private String utility;

    @Lob
    @Column(name = "design")
    private String design;

    @Lob
    @Column(name = "other")
    private String other;

    @Column(name = "video")
    private String video;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategory;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flash_sale_id")
    private FlashSale flashSale;
    
    @OneToMany(mappedBy = "product")
    private Set<Rating> ratings = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Cart> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductThumbnail> productThumbnails = new LinkedHashSet<>();

    @Formula(value = "(select coalesce(AVG(r.point), 0) FROM Rating r WHERE r.product_id = id)")
    private Integer averageRating;
    
    @Formula(value = "(select coalesce(COUNT(1), 0) FROM Rating r WHERE r.product_id = id)")
    private Integer totalRating;

    @Column(name = "weight")
    private Double weight;

}