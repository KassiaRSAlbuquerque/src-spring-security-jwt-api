package io.github.kassiarsalbuquerque.VendasSpringWeb.security.inMemoryAndBasicAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.kassiarsalbuquerque.VendasSpringWeb.service.UsuarioService;

//@EnableWebSecurity
public class InMemoryAndBasicAuthenticationConfig {//extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService userService;
	
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//AUTENTICACAO DE USUARIO EM MEMORIA
//    	auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("fulano")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER","ADMIN");
    	
    	//CONFIGURACAO DE AUTENTICACAO BASIC DE USUARIO DA BASE DE DADOS ATRAVES DO LOGIN
    	auth.userDetailsService(this.userService)
    			.passwordEncoder(passwordEncoder());
    }

    //@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	//AUTORIZACAO VIA FORMULARIO DE LOGIN 
    	
    	// TUDO LIBERADO
//    	http.csrf().disable()
//        	.authorizeRequests()
//        		.antMatchers("/clientes/**")
//        			//.authenticated()
//        			.permitAll()
//        	.and()
//        		.formLogin();
    	
    	
    	// LIBERADO COM RESTRICOES
//    	http.csrf().disable()
//		    	.authorizeRequests()
//			    	.antMatchers("/clientes/**")
//			    		.hasAnyRole("USER","ADMIN")
//			    	.antMatchers("/produtos/**")
//			    		.hasRole("ADMIN")
//			    	.antMatchers("/itensPedido/**")
//			    		.hasAnyRole("USER","ADMIN")
//		    	.and()
//		    		.formLogin();
    	
    	//AUTORIZACAO BASIC - PASSANDO A AUTENTICACAO NO HEADER OU POR TELA SIMPLES DE LOGIN
    	
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
    		.httpBasic();
    }
}