package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item_pedido")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(value = Include.NON_NULL)
public class ItemPedido implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)//H2
	//@GeneratedValue(strategy = GenerationType.IDENTITY)//MySql
	private Integer id;
	
	@ManyToOne
	//@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	
	@ManyToOne
	//@JoinColumn(name = "produto_id")
	private Produto produto;
	
	@Column
	private Integer qtdade;

	public ItemPedido(Pedido pedido, Produto produto, Integer qtdade) {
		super();
		this.pedido = pedido;
		this.produto = produto;
		this.qtdade = qtdade;
	}

	public ItemPedido() {
		super();
	}
}