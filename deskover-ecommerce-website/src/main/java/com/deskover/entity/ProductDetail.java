package com.deskover.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_details")
public class ProductDetail implements Serializable {
    private static final long serialVersionUID = -8490477268521790761L;
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "cpu")
    private String cpu;

    @Column(name = "ram")
    private String ram;

    @Column(name = "hard_drive")
    private String hardDrive;

    @Column(name = "graphics_card")
    private String graphicsCard;

    @Column(name = "screen")
    private String screen;

    @Column(name = "web_cam")
    private String webCam;

    @Lob
    @Column(name = "front_camera")
    private String frontCamera;

    @Lob
    @Column(name = "rear_camera")
    private String rearCamera;

    @Lob
    @Column(name = "camera_features")
    private String cameraFeatures;

    @Lob
    @Column(name = "communication_port")
    private String communicationPort;

    @Column(name = "operating_system")
    private String operatingSystem;

    @Column(name = "pin")
    private String pin;

    @Column(name = "weight")
    private String weight;

    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    @Column(name = "modified_by", length = 50)
    private String modifiedBy;
    
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}