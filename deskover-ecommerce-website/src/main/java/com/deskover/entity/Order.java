package com.deskover.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    private static final long serialVersionUID = -6589753132530202456L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_code", nullable = false, length = 11)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

}