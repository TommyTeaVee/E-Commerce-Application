package com.app.entites;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "farms")
@NoArgsConstructor
@AllArgsConstructor
public class Farm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long farmId;

	@NotBlank
	@Size(min = 5, message = "Farms  name must contain atleast 5 characters")
	private String farmName;

	@OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
	private List<Product> products;


}