package net.alten.gestion.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.alten.gestion.dto.ProductDTO;
import net.alten.gestion.service.ProductService;


@RestController
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(Principal principal){
		
		
		return ResponseEntity.ok().body(productService.getAllProducts());
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> getOneProduct(@PathVariable("id")Long id){
		
		return ResponseEntity.ok().body(productService.getOneProduct(id).get());
	}
	

	@PostMapping("/products")
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO,Principal principal){
		
		productService.checkIfAdminUser(principal.getName());
		
		return ResponseEntity.ok().body(productService.addProduct(productDTO));
	}
	
	@PatchMapping("/products/{id}")
	public ResponseEntity<ProductDTO> updateUpdate(@PathVariable("id")Long id, @RequestBody ProductDTO productDTO,Principal principal){
		
		productService.checkIfAdminUser(principal.getName());
		
		return ResponseEntity.ok().body(productService.updateProduct(id,productDTO));
	}
	
	
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id")Long id,Principal principal){
		
		productService.checkIfAdminUser(principal.getName());
		
		productService.deleteProduct(id);
		
		return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
	}
}
