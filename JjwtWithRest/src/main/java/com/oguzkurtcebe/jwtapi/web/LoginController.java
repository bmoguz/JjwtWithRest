package com.oguzkurtcebe.jwtapi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oguzkurtcebe.jwtapi.dto.LoginRequest;
import com.oguzkurtcebe.jwtapi.security.auth.TokenManager;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private AuthenticationManager autmanager;
	@Autowired
	private TokenManager tokenManager;


    @PostMapping
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		try {
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
			String token = tokenManager.genereteToken(loginRequest.getUsername());
			return ResponseEntity.ok(token);

		} catch (Exception e) {
			throw e;
		}
	}
}
