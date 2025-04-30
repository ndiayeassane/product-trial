package net.alten.gestion.service;

import java.util.List;
import java.util.Optional;

import net.alten.gestion.dto.ProductDTO;

public interface ProductService {

	ProductDTO addProduct(ProductDTO productDTO);
	
	ProductDTO updateProduct(Long id,ProductDTO productDTO);
	
	List<ProductDTO> getAllProducts();
	
	Optional<ProductDTO> getOneProduct(Long id);
	
	void deleteProduct(Long id);
	
	Boolean checkIfAdminUser(String email) ;
}
