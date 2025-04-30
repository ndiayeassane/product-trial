package net.alten.gestion.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.alten.gestion.dto.AppUserDTO;
import net.alten.gestion.dto.ProductDTO;
import net.alten.gestion.handlerException.ApiRequestException;
import net.alten.gestion.mapper.AppUserMapper;
import net.alten.gestion.mapper.ProductMapper;
import net.alten.gestion.repository.AppUserRepository;
import net.alten.gestion.repository.ProductRepository;

import net.alten.gestion.service.ProductService;
import net.alten.gestion.utils.Constants;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	
	private final AppUserRepository appUserRepository;
	
	private final ProductMapper productMapper;
	
	private final AppUserMapper appUserMapper;
	
	
	@Override
	public Boolean checkIfAdminUser(String email) {
		
	 
	Optional<AppUserDTO> optionalUser =	 appUserRepository.findByEmail(email)
							.map(appUserMapper::toDto);
		 
		 if(!optionalUser.get().getEmail().equals(Constants.ADMIN)) throw new ApiRequestException(HttpStatus.FORBIDDEN.toString());
		 
		 
		 return true;
	}
	
	@Override
	public ProductDTO addProduct(ProductDTO productDTO) {
		
		return productMapper.toDto(productRepository.save(
				productMapper.toEntity(productDTO)
				));
	}

	@Override
	public ProductDTO updateProduct(Long id,ProductDTO productDTO) {
		
		if(id == null) throw new ApiRequestException("ID_NO_FOUND");
		
		Optional<ProductDTO> optionalProduct = productRepository.findById(id)
				.map(productMapper::toDto);
		
		if(!optionalProduct.isPresent()) throw new ApiRequestException("PRODUCT_NO_FOUND");
		
		return productMapper.toDto(productRepository.save(
				productMapper.toEntity(productDTO)
				));
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		
		return productRepository.findAll()
				.stream()
				.map(productMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<ProductDTO> getOneProduct(Long id) {
		
		if(id == null) throw new ApiRequestException("ID_NO_FOUND");
		
		Optional<ProductDTO> optionalProduct = 
				productRepository.findById(id).map(productMapper::toDto);
		
		if(!optionalProduct.isPresent()) throw new ApiRequestException("PRODUCT_NO_FOUND");

		return optionalProduct;
	}

	@Override
	public void deleteProduct(Long id) {
		
		if(id == null) throw new ApiRequestException("ID_NO_FOUND");
		
		Optional<ProductDTO> optionalProduct = 
				productRepository.findById(id).map(productMapper::toDto);
		
		if(!optionalProduct.isPresent()) throw new ApiRequestException("PRODUCT_NO_FOUND");

		productRepository.deleteById(id);
		
	}

}
