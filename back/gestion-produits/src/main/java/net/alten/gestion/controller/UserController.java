package net.alten.gestion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.alten.gestion.dto.AppUserDTO;
import net.alten.gestion.service.AppUserService;

@RestController
@AllArgsConstructor

public class UserController {

	private final AppUserService appUserService;
	
	@PostMapping("/account")
	public ResponseEntity<?> signup(@RequestBody AppUserDTO appUserDTO){
		
		return ResponseEntity.ok().body(appUserService.signUpUser(appUserDTO));
	}
	

}
