package net.alten.gestion.domain;

import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String  code;
	
	private String  name;
	
	@Column(name="description",columnDefinition = "TEXT")
	
	private String  description;
	
	private String  image;
	
	private String  category;
	
	private BigInteger  price;
	
	private int  quantity;
	
	private String  internalReference;
	
	private Long  shellId;
	
	@Enumerated(EnumType.STRING)
	private InventoryStatus  inventoryStatus;
	
	private int  rating;
	
	@CreatedDate
	private LocalDate  createdAt = LocalDate.now();
	
	private LocalDate  updatedAt;
}
