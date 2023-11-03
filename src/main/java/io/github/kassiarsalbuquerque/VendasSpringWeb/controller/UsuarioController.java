package io.github.kassiarsalbuquerque.VendasSpringWeb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.dto.Credenciais;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.dto.Token;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Usuario;
import io.github.kassiarsalbuquerque.VendasSpringWeb.exception.SenhaInvalidaException;
import io.github.kassiarsalbuquerque.VendasSpringWeb.service.JwtService;
import io.github.kassiarsalbuquerque.VendasSpringWeb.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Usuario createUsuario(@RequestBody @Valid Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return this.usuarioService.salvar(usuario);
	}
	
	@PostMapping(value="{auth}")
	public Token autenticar(@RequestBody Credenciais credenciais) {
		try {
			Usuario usuario = new Usuario(credenciais.getLogin(),credenciais.getSenha(), true);
			
			usuarioService.autenticar(usuario);
			String tokenString = jwtService.gerarToken(usuario);
			
			return new Token(usuario.getLogin(), tokenString);
		} catch (UsernameNotFoundException | SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}
}