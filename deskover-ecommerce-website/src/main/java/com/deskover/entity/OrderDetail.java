package com.deskover.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 3544939440474585363L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    @NotBlank(message = "Không bỏ trống địa chỉ nhận hàng")
    @Column(name = "address", nullable = false)
    private String address;
    
	@NotBlank(message = "Không bỏ trống Tỉnh/TP nhận hàng")
    @Column(name = "province", length = 128)
    private String province;
	
	@NotBlank(message = "Không bỏ trống Quận/Huyện nhận hàng")
    @Column(name = "district", length = 128)
    private String district;
	
	@NotBlank(message = "Không bỏ trống Phường/Xã nhận hàng")
    @Column(name = "ward", length = 128)
    private String ward;
	
	@NotBlank(message = "Không bỏ trống số điện thoại nhận hàng")
    @Column(name = "tel", nullable = false, length = 10)
    private String tel;

}