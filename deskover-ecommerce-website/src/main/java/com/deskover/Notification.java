package com.deskover;

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

}