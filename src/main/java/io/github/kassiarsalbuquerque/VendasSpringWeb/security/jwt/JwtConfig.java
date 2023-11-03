package io.github.kassiarsalbuquerque.VendasSpringWeb.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.kassiarsalbuquerque.VendasSpringWeb.service.JwtService;
import io.github.kassiarsalbuquerque.VendasSpringWeb.service.UsuarioService;

@EnableWebSecurity
public class JwtConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService userService;
	@Autowired
	private JwtService jwtService;
	
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public OncePerRequestFilter jwtFilter(){
    	return new JwtAuthFilter( jwtService, userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//CONFIGURACAO DE AUTENTICACAO BASIC DE USUARIO DA BASE DE DADOS ATRAVES DO LOGIN
//    	auth.userDetailsService(this.userService)
//    			.passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//AUTORIZACAO COM TOKEN - PASSANDO A AUTENTICACAO "BEARER TOKEN" NO HEADER
    	
    	http.csrf().disable()
		    	.authorizeRequests()
			    	.antMatchers("/clientes/**")
			    		.hasAnyRole("USER","ADMIN")
			    	.antMatchers("/produtos/**")
			    		.hasRole("ADMIN")
			    	.antMatchers("/itensPedido/**")
			    		.hasAnyRole("USER","ADMIN")
			    	.antMatchers( HttpMethod.POST, "/usuarios/**")
			    		.permitAll()
			    	.anyRequest().authenticated()
		    	.and()
		    		.sessionManagement()//sessao statless--> toda vez q chamar tem q passar o token
		    		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)// desabilita a politica de criacao de sessao
		    	.and()
		    		.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}