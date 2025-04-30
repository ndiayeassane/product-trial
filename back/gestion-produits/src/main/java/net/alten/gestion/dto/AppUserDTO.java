package net.alten.gestion.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

	public AppUserDTO(String email, String password) {
		
		this.email = email;
		this.password = password;
	}

	private Long id;
	private String username;
	private String firstName;
	private String email;
	private String password;
}
