package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="cliente")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(value = Include.NON_NULL)
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)//H2
	//@GeneratedValue(strategy = GenerationType.IDENTITY)//MySql
	private Integer id;
	
	@Column(name = "nome", length = 100)
	@NotEmpty(message = "Campo nome é obrigatório.")
	private String nome;
	
	@Column(name = "cpf" , length = 11)
	@NotEmpty(message = "Campo cpf é obrigatório.")
	@CPF(message = "Informe cpf valido.")
	private String cpf;
	
	@OneToMany(mappedBy = "cliente", targetEntity = Pedido.class,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Pedido> pedidos;
	
	public Cliente(String nome, String cpf) {
		super();
		this.nome = nome;
		this.cpf = cpf;
	}

	public Cliente() {
		super();
	}
}