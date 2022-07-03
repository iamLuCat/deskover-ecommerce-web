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
@Table(name = "orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 3887345717822508619L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_code", nullable = false, length = 11)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private PaymentMethods payment;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_id")
    private ShippingMethods shipping;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusOrder status_order;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;
    
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "status", nullable = false)
    @CreationTimestamp
    private Timestamp status;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

}