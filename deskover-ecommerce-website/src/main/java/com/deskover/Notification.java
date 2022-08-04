<<<<<<< HEAD:deskover-ecommerce-website/src/main/java/com/deskover/model/entity/database/Notification.java
package com.deskover.model.entity.database;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
=======
package com.deskover;
>>>>>>> parent of bd0094b3 (Merge pull request #214 from QuangVu1235/hai-dev-be):deskover-ecommerce-website/src/main/java/com/deskover/Notification.java

import com.deskover.model.entity.database.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification implements Serializable {
    private static final long serialVersionUID = -78816057551216207L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "order_code", nullable = false)
    private String orderCode;

    @Column(name = "is_watched")
    private Boolean isWatched;
    
    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}