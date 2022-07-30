package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    
    @Column(name = "order_qr_code")
    private String qrCode;
    
    @Column(name="note")
    private String note;
    
    @Column(name="shipping_note")
    private String shipping_note;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;
    
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice = 0.0;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity = 0;
    
    @Column(name = "label", nullable = false)
    private String label;
    
    @Column(name = "fee", nullable = false)
    private Double fee;
    
    @Column(name = "estimated_pick_time", nullable = false)
    private String estimated_pick_time;
    
    @Column(name = "estimated_deliver_time", nullable = false)
    private String estimated_deliver_time;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    
    @NotNull(message = "Không bỏ trống phương thức thanh toán")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private PaymentMethods payment;

    @NotNull(message = "Không bỏ trống phương thức vận chuyển")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_id")
    private ShippingMethods shipping;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private OrderStatus orderStatus;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private OrderDetail orderDetail;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> products = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_payment_id")
    private StatusPayment statusPayment;

}