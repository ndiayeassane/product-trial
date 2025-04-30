package net.alten.gestion.mapper;

import org.mapstruct.Mapper;

import net.alten.gestion.domain.AppUser;
import net.alten.gestion.dto.AppUserDTO;


@Mapper(componentModel = "spring", uses = {})
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
	
	AppUserDTO toDto(AppUser user);
	AppUser toEntity(AppUserDTO userDTO);
	
	  default AppUser fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        AppUser user = new AppUser();
	        user.setId(id);
	        return user;
	    }

}
