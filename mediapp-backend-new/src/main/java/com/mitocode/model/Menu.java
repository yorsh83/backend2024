package com.mitocode.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Menu {

	@Id
	@EqualsAndHashCode.Include
	private Integer idMenu;

	@Column(nullable = false, length = 20)
	private String icon;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false, length = 30)
	private String url;
}
