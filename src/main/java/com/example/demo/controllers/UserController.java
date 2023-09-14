package com.example.demo.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.User;
import com.example.demo.entities.dto.UserTokenDTO;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.implementation.UserServiceImpl;
import com.example.demo.util.Encryption;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private SecretKey secretKey;
	
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	private String getJWTToken(User userEntity) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(userEntity.getRole());
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getEmail())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration)).signWith(this.secretKey)
				.compact();
		return "Bearer " + token;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> korisnikLogger) {
		String email = korisnikLogger.get("email");
		String password = korisnikLogger.get("password");
		User user = userRepository.findByEmail(email);
		if (user != null && Encryption.validatePassword(password, user.getPassword())) {
			String token = getJWTToken(user);
			UserTokenDTO userLogin = new UserTokenDTO(user.getId(), email, token, user.getRole());
			
			return new ResponseEntity<>(userLogin, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
		
	}
	
	
}
