package com.oguzkurtcebe.jwtapi.security.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
private Map<String,String>users=new HashMap<>();
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;

@PostConstruct
public void init() {
	users.put("oguz", passwordEncoder.encode("123"));
}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(users.containsKey(username)) {
			return new User(username,users.get(username),new ArrayList<>());
		}
	 throw  new UsernameNotFoundException(username);
	}

}
