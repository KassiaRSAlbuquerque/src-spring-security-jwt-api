package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="usuario")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(value = Include.NON_NULL)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)//H2
	//@GeneratedValue(strategy = GenerationType.IDENTITY)//MySql
	private Integer id;
	
	@Column(name = "login", length = 15)
	@NotEmpty(message = "Campo login é obrigatório.")
	private String login;
	
	@Column(name = "senha", length = 80)
	@NotEmpty(message = "Campo senha é obrigatório.")
	private String senha;
	
	@Column(name = "admin")
	private boolean admin;

	public Usuario() {
		super();
	}

	public Usuario(String login, String senha, boolean admin) {
		super();
		this.login = login;
		this.senha = senha;
		this.admin = admin;
	}
}