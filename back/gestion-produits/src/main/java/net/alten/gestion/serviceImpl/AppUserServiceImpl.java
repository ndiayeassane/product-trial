package net.alten.gestion.serviceImpl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.alten.gestion.dto.AppUserDTO;
import net.alten.gestion.handlerException.ApiRequestException;
import net.alten.gestion.mapper.AppUserMapper;
import net.alten.gestion.repository.AppUserRepository;
import net.alten.gestion.service.AppUserService;

@AllArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {
	
	private final AppUserRepository appUserRepository;
	
	private final AppUserMapper appUserMapper;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	 @Override
	    public UserDetailsService userDetailsService() {
	        return new UserDetailsService() {
	            @Override
	            public UserDetails loadUserByUsername(String username) {
	                return appUserRepository.findByEmail(username)
	                        .orElseThrow(() -> new ApiRequestException("User not found"));
	            }
	        };
	    }

	@Override
	public AppUserDTO signUpUser(AppUserDTO appUserDTO) {
		
		boolean userExists = appUserRepository.findByEmail(appUserDTO.getEmail())
				.isPresent();
		
		if(userExists) {
			throw new ApiRequestException("email already taken");
		}
		String encodingPassword = bCryptPasswordEncoder
				
				.encode(appUserDTO.getPassword());
		
		appUserDTO.setPassword(encodingPassword);
		
		return appUserMapper.toDto(appUserRepository.save(appUserMapper.toEntity(appUserDTO)));
	
		
	}

	@Override
	public Optional<AppUserDTO> loadUserByUsername(String email) {
		
		Optional<AppUserDTO> optionalUserDTO = Optional.of(appUserMapper.toDto(appUserRepository.findByEmail(email).get()));
		
		if(!optionalUserDTO.isPresent()) throw new UsernameNotFoundException(email);
		
		return optionalUserDTO;
				
	}

}
