package com.deskover.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating implements Serializable {
    private static final long serialVersionUID = -8794052286728363397L;
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "actived", nullable = false)
    private Boolean actived = false;

    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Timestamp modifiedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}