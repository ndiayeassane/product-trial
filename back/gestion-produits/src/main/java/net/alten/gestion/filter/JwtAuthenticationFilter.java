package net.alten.gestion.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.alten.gestion.utils.JWTUtils;


@AllArgsConstructor

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("attemptAuthentication !!");
		String username = request.getParameter("email");
		String password = request.getParameter("password");
		
	


		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		try {
			log.info("successfulAuthentication !!!");
			Algorithm algo1 = Algorithm.HMAC256(JWTUtils.SECRET);
			String jwtAccessToken =JWT.create()
					.withSubject(authResult.getName())
					.withClaim("Method", request.getMethod())
					.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtils.EXPIRE_ACCESS_TOKEN))
					.withIssuer(request.getRequestURL().toString())
					.withClaim("role","USER")

					.sign(algo1);
			
			Map<String, String> idToken = new HashMap<>();
			idToken.put("access-token", jwtAccessToken);
			response.setContentType("application/json");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "*");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Max-Age", "3600");
			new ObjectMapper().writeValue(response.getOutputStream(), idToken);
			
			log.info("successfulAuthentication final !!!");
			
		} catch (Exception e) {
			
			response.setHeader("error-message", e.getMessage());
			response.sendError(response.getStatus(),e.getMessage());
		}
	}

}