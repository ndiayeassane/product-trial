package net.alten.gestion.dto;

import java.math.BigInteger;
import java.time.LocalDate;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.alten.gestion.domain.InventoryStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

private Long id;
	
	private String  code;
	
	private String  name;
	
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
	

	private LocalDate  createdAt;
	
	private LocalDate  updatedAt;
}
