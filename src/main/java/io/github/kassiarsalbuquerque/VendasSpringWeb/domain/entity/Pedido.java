package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.enums.StatusPedido;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="pedido")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(value = Include.NON_NULL)
public class Pedido implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)//H2
	//@GeneratedValue(strategy = GenerationType.IDENTITY)//MySql
	private Integer id;
	
	@ManyToOne
	@NotNull(message = "o cliente é obrigatório.")
	//@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column(name = "data_pedido")
	private LocalDateTime dataPedido;
	
	@Column(name = "total" , precision = 20 , scale = 2)
	private Long total;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusPedido status;
	
	@OneToMany(mappedBy = "pedido", targetEntity = ItemPedido.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ItemPedido> itens;
	
	public Pedido(Cliente cliente, Long total) {
		super();
		this.cliente = cliente;
		this.total = total;
	}

	public Pedido() {
		super();
	}
}