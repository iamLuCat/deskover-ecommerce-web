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
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

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
@Table(name = "orders")
@NamedStoredProcedureQuery(name = "Order.getTotalPrice_Shiping_PerMonth", 
  procedureName = "getTotalPrice_Shiping_PerMonth", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "month", type = String.class),
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "year", type = String.class),
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "modified_by", type = String.class),
  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "total", type = String.class),
  
  })
public class Order implements Serializable {
	
    private static final long serialVersionUID = 3887345717822508619L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_code", nullable = false, length = 11)
    private String orderCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
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
    private OrderStatus orderStatus;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;
    
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;
    
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

}