package net.alten.gestion.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.alten.gestion.dto.AppUserDTO;

public interface AppUserService {

	AppUserDTO signUpUser(AppUserDTO appUserDTO);
	
	Optional<AppUserDTO> loadUserByUsername(String email);
	
	 UserDetailsService userDetailsService();
}
