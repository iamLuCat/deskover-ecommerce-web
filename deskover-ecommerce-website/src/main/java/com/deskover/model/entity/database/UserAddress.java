package com.deskover.model.entity.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_address")
public class UserAddress implements Serializable {
    private static final long serialVersionUID = 568706130123470738L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "province_id", nullable = false)
    private Province provinceId;
    
    @NotBlank(message = "Không bỏ trống tên")
    @Column(name = "fullname", length = 128)
    private String fullname;
    
    @NotBlank(message = "Không bỏ trống địa chỉ")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "Không bỏ trống Tỉnh/TP")
    @Column(name = "province", length = 128)
    private String province;

    @NotBlank(message = "Không bỏ trống Quận/Huyện")
    @Column(name = "district", length = 128)
    private String district;
    
    @Column(name = "district_id", nullable = false)
    private Long districtId;
    
    @NotBlank(message = "Không bỏ trống Phường/Xã")
    @Column(name = "ward", length = 128)
    private String ward;
    
    @Column(name = "ward_id", nullable = false)
    private Long wardId;

    @NotBlank(message = "Không bỏ trống số điện thoại")
    @Column(name = "tel", length = 10)
    private String tel;

    @Column(name = "email", nullable = false, length = 50)
    private String email;
    
    @Column(name = "choose")
    private Boolean choose;
    
    @Column(name = "actived")
    private Boolean actived;

}