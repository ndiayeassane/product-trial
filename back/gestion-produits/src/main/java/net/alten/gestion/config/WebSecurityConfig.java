package net.alten.gestion.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import lombok.RequiredArgsConstructor;
import net.alten.gestion.filter.JwtAuthenticationFilter;
import net.alten.gestion.filter.JwtAuthorizationFilter;
import net.alten.gestion.service.AppUserService;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig  {
	

    private final AppUserService appUserService;
    
    private final AuthenticationConfiguration authenticationConfiguration;
    	
	    
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	
	    	JwtAuthenticationFilter authenticationFilter= new JwtAuthenticationFilter(authenticationManager());
			authenticationFilter.setFilterProcessesUrl("/token");
			
	        http.csrf(AbstractHttpConfigurer::disable);
	       
	        http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        
	        .headers(headers -> headers
	                .frameOptions(frameOptions -> frameOptions
	                    .disable()
	                )
	            )
            .authenticationProvider(authenticationProvider())
	        .authorizeHttpRequests(request -> request.requestMatchers("/token").permitAll()
	                								 		 .requestMatchers("/account").permitAll()
	                								 		//.requestMatchers("/**").permitAll()
	                								 		
	                								 		 .anyRequest().authenticated());

	        http.addFilter(authenticationFilter);
			JwtAuthorizationFilter filter = new JwtAuthorizationFilter();
			http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(appUserService.userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager()
	            throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	
	
	

}
