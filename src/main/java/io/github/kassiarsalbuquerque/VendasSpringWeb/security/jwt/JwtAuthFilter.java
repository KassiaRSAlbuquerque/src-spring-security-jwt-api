package io.github.kassiarsalbuquerque.VendasSpringWeb.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.kassiarsalbuquerque.VendasSpringWeb.service.JwtService;
import io.github.kassiarsalbuquerque.VendasSpringWeb.service.UsuarioService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UsuarioService usuarioService;
	

	public JwtAuthFilter(JwtService jwtService, UsuarioService usuarioService) {
		super();
		this.jwtService = jwtService;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)throws ServletException, IOException {
		// METODO RESPONSAVEL POR INTERCEPTAR TODA REQUEST Q CHEGA NA API
		String autorization = request.getHeader("Authorization");
		
		if (autorization != null && autorization.startsWith("Bearer")) {
			String token = autorization.split(" ")[1];
			
			boolean isValid = jwtService.tokenValido(token);
			
			// AUTENTICA
			if (isValid) {
				String loginUsuarioToken = jwtService.obterLoginUsuario(token);
				UserDetails usuario = usuarioService.loadUserByUsername(loginUsuarioToken);
				
				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);//seta usuario com suas autorizacoes no contexto do spring security
			}
		}
		
		// DESPACHA A REQUEST
		filterChain.doFilter(request, response);
	}

}
