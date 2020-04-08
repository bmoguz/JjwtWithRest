package com.oguzkurtcebe.jwtapi.security.auth;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Service
public class TokenManager {
	private static Key key = MacProvider.generateKey();
	private static final int validity = 5 * 60 * 1000;

	public String genereteToken(String username) {

		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + validity))
				.setIssuer("oguzkurtcebe").setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS512, key).compact();
	}

	public boolean tokenValidate(String token) {
		if (getUserFromToken(token) != null && isExpired(token)) {
			return true;
		}
		return false;	

	}

	public String getUserFromToken(String token) {
		Claims claims = getClaims(token);
		return claims.getSubject();
	}

	public boolean isExpired(String token) {
		Claims claims = getClaims(token);
		return claims.getExpiration().after(new Date(System.currentTimeMillis()));
	}

	private Claims getClaims(String token) {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claims;
	}

}
