package com.oguzkurtcebe.jwtapi.security.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component	
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenManager tokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String autHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (autHeader != null && autHeader.contains("Bearer")) {
			token = autHeader.substring(7);
			try {
				username = tokenManager.getUserFromToken(token);
			} catch (Exception e) {
				System.out.println("Hata:" + e.getMessage());
			}

		}
		if (username != null && token != null
				&& SecurityContextHolder.getContext().getAuthentication()==null) {
     if(tokenManager.tokenValidate(token)) {
				UsernamePasswordAuthenticationToken upassToken = new UsernamePasswordAuthenticationToken(username, null,
						new ArrayList<>());
				upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upassToken);
     }	
		}
		filterChain.doFilter(request, response);
	}

}
