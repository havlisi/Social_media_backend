package com.example.demo.services.implementation;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import com.example.demo.entities.User;
import com.example.demo.entities.dto.UserTokenDTO;
import com.example.demo.exceptions.UnauthorizedUserException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.util.Encryption;
import io.jsonwebtoken.Jwts;

@Service
public class UserServiceImpl  implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private SecretKey secretKey;
	
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	
	
	public String getJWTToken(User userEntity) {
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

	public UserTokenDTO login(Map<String, String> korisnikLogger) throws Exception {
		String email = korisnikLogger.get("email");
		String password = korisnikLogger.get("password");
		User user = userRepository.findByEmail(email);
		if (user != null && Encryption.validatePassword(password, user.getPassword())) {
			String token = getJWTToken(user);
			UserTokenDTO userLogin = new UserTokenDTO(user.getId(), email, token, user.getRole());
			
			return userLogin;
		}
		throw new UnauthorizedUserException("Unauthorized user");
	}
}
