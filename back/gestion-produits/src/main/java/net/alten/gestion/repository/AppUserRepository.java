package net.alten.gestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.alten.gestion.domain.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByEmail(String email);
}
