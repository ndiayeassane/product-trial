package net.alten.gestion.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;



import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.alten.gestion.utils.JWTUtils;


@RequiredArgsConstructor
public class JwtAuthorizationFilter  extends OncePerRequestFilter {
	



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/token") ) {
			filterChain.doFilter(request, response);
		}
		else {
		String authorizatioToken = request.getHeader(JWTUtils.HEADER_AUTH);
		if(authorizatioToken!= null && authorizatioToken.startsWith(JWTUtils.PREFIX)) {
			try {
				String jwt = authorizatioToken.substring(JWTUtils.PREFIX.length());
				Algorithm algorithm = Algorithm.HMAC256(JWTUtils.SECRET);
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT= jwtVerifier.verify(jwt);
				String username= decodedJWT.getSubject();
			
				Collection<GrantedAuthority> authorities = new ArrayList<>();
				
				authorities.add(new SimpleGrantedAuthority(username));
				
				UsernamePasswordAuthenticationToken authenticationToken = 
						new UsernamePasswordAuthenticationToken(username,null, authorities);
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
				
				
				
			} catch (RuntimeException e) {
				
				
	            response.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
	            response.getWriter().write(convertObjectToJson(e.getMessage()));
				
			}
		}
		else {
			filterChain.doFilter(request, response);
		}
		
	 }
	}
	
	 public String convertObjectToJson(Object object) throws JsonProcessingException {
	        if (object == null) {
	            return null;
	        }
	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(object);
	    }

}
