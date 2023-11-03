package io.github.kassiarsalbuquerque.VendasSpringWeb.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Usuario;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IUsuarioRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.exception.SenhaInvalidaException;

@Service
public class UsuarioService implements UserDetailsService{

	//CARREGA O USUARIO DA BASE DE DADOS ATRAVES DO LOGIN
	@Autowired
	private IUsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}
	
	
	public UserDetails autenticar(Usuario usuario){
		UserDetails userBanco = loadUserByUsername(usuario.getLogin());
		boolean senhasIguais = passwordEncoder.matches( usuario.getSenha(), userBanco.getPassword());
		
		if (senhasIguais)
			return userBanco;
		 
		throw new SenhaInvalidaException();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// RECUPERANDO USUARIO DA BASE
		Usuario usuarioConsultado = this.usuarioRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base."));
		
		String[] roles = usuarioConsultado.isAdmin()? new String[] {"USER", "ADMIN"}: new String[] {"USER"};
		
		return User.builder()
					.username(usuarioConsultado.getLogin())
					.password(usuarioConsultado.getSenha())
					.roles(roles)
					.build();
		
		// SIMULANDO UM USUARIO DA BASE
//		if (!username.equals("cicrano")) {
//			throw new UsernameNotFoundException("Usuário não encontrado na base.");
//		}
		
//		return User.builder()
//				.username("cicrano")
//				.password(passwordEncoder.encode("123"))
//				.roles("USER","ADMIN")
//				.build();
	}
}