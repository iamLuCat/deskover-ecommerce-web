package com.deskover.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "brand")
public class Brand implements Serializable {
	private static final long serialVersionUID = -257951775645271577L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotBlank(message = "Không bỏ trống tên thương hiệu")
	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "description", length = 150)
	private String description;

	@NotBlank(message = "Không để trống slug")
	@Column(name = "slug", nullable = false, length = 50)
	private String slug;

	@Column(name = "actived", nullable = false)
	private Boolean actived = false;

	@Column(name = "created_at", nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "modified_at")
	private Timestamp modifiedAt;

	@Column(name = "modified_user", length = 50)
	private String modifiedUser;

	@JsonIgnore
	@OneToMany(mappedBy = "brand")
	private Set<Product> products = new LinkedHashSet<>();

}