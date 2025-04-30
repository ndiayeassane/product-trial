package net.alten.gestion.mapper;

import org.mapstruct.Mapper;

import net.alten.gestion.domain.Product;
import net.alten.gestion.dto.ProductDTO;


@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

	ProductDTO toDto(Product product);
	Product toEntity(ProductDTO productDTO);
	
	  default Product fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        Product product = new Product();
	        product.setId(id);
	        return product;
	    }
}
