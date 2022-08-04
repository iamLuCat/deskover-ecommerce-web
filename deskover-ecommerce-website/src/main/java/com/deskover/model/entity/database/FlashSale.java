package com.deskover.model.entity.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "flash_sale")
public class FlashSale implements Serializable {
    private static final long serialVersionUID = 7245682534343526382L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @NotNull(message = "Không bỏ trống ngày tạo")
    @Column(name = "start_date", nullable = false)
    @CreationTimestamp
    private Timestamp startDate;
    
    @NotNull(message = "Không bỏ trống ngày kết thúc")
    @Column(name = "end_date")
    private Timestamp endDate;
  
    @NotNull(message = "Không bỏ trống kích hoạt")
    @Column(name = "actived")
    private Boolean actived;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @OneToMany(mappedBy = "flashSale")
    private Set<Product> products = new LinkedHashSet<>();

}