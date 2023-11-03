package io.github.kassiarsalbuquerque.VendasSpringWeb.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.github.kassiarsalbuquerque.VendasSpringWeb.VendasSpringWebApplication;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;
	@Value("${security.jwt.chave.assinatura}")
	private String chaveAssinatura;
	// o q seria essa chave no mundo real de projeto? clienteId?

	//-------------------------------------------------CODIFICA TOKEN-----------------------------------------------------
	public String gerarToken(Usuario usuario) {
		
		long exp = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp);
		Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		
		//Adiciona mais informacao ao token atraves do Claims()
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("emaildousuario", "usuario@gmail.com");
		claims.put("roles", "admin");
		
		return Jwts.builder()
					.setSubject(usuario.getLogin())
					//.setClaims(claims)
					.setExpiration(date)
					.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
					.compact();
	}
	
	//-------------------------------------------------DECODIFICA TOKEN-----------------------------------------------------
	public boolean tokenValido(String token) {
		try {
			Claims claims = obterClaims(token);
			
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime dateTimeExpiracao = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			//System.out.println("dateTimeExpiracao :" +dataExpiracao);
			
			return dateTimeExpiracao.isAfter(LocalDateTime.now());
		} catch (Exception e) {
			return false;
		}
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {

		return obterClaims(token).getSubject();
	}
	
	private Claims obterClaims(String token) throws ExpiredJwtException{

		// decodifica o token
		return Jwts.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public static void main(String[] args) {
		
		Usuario usuario = new Usuario();
		usuario.setLogin("teste123");
		
		ConfigurableApplicationContext contexto = SpringApplication.run(VendasSpringWebApplication.class);
		
		JwtService service = contexto.getBean(JwtService.class);
		String token = service.gerarToken(usuario);
		System.out.println("Token :" +token);
		
		System.out.println("Token valido :" + service.tokenValido(token));
		System.out.println("Obter usuario do token :" + service.obterLoginUsuario(token));
	}
}
