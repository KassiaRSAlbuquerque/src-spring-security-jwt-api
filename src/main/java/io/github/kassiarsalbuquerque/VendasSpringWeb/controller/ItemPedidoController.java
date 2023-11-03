package io.github.kassiarsalbuquerque.VendasSpringWeb.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.ItemPedido;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Pedido;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Produto;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.enums.StatusPedido;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IClienteRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IItemPedidoRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IPedidoRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IProdutoRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.exception.RegraNegocioException;


@RestController
@RequestMapping("/itensPedido")
public class ItemPedidoController {

	@Autowired
	private IItemPedidoRepository itemPedidoRepository;
	@Autowired
	private IProdutoRepository produtoRepository;
	@Autowired
	private IClienteRepository clienteRepository;
	@Autowired
	private IPedidoRepository pedidoRepository;
	
	@Transactional
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ItemPedido createItemPedido(@RequestBody ItemPedido itemPedido) {
		
		Produto produtoConsultado = this.produtoRepository.findById(itemPedido.getProduto().getId()).orElseThrow(() -> new RegraNegocioException("Codigo de produto invalido!"));

		if (null != itemPedido.getPedido().getId()) {
			Pedido pedidoConsultado = this.pedidoRepository.findById(itemPedido.getPedido().getId()).orElseThrow(() -> new RegraNegocioException("Codigo de pedido invalido!"));
			itemPedido.setPedido(pedidoConsultado);
		}else {
			itemPedido.getPedido().setTotal(Long.parseLong("0"));
			this.clienteRepository.findById(itemPedido.getPedido().getCliente().getId()).orElseThrow(() -> new RegraNegocioException("Codigo de cliente invalido!"));
		}
		itemPedido.getPedido().setTotal(itemPedido.getPedido().getTotal()+(produtoConsultado.getPreco() * itemPedido.getQtdade()));
		
		itemPedido.getPedido().setDataPedido(LocalDateTime.now());
		itemPedido.getPedido().setStatus(StatusPedido.REALIZADO);
		this.pedidoRepository.save(itemPedido.getPedido());
		
		return this.itemPedidoRepository.save(itemPedido);
	}
	
	@PatchMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateItemPedido(@PathVariable Integer id, @RequestBody ItemPedido itemPedido) {
		
		this.itemPedidoRepository.findById(id)
								.map(itemPedidoConsultado -> {
									itemPedido.setId(itemPedidoConsultado.getId());
									return this.itemPedidoRepository.save(itemPedido);})
								.orElseThrow(() -> new RegraNegocioException("Codigo de item pedido invalid!"));
	}
	
	@DeleteMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteItemPedido(@PathVariable Integer id) {
		
		this.itemPedidoRepository.findById(id)
								.map(itemPedido -> {
									this.itemPedidoRepository.delete(itemPedido);
									return itemPedido;})
								.orElseThrow(() -> new RegraNegocioException("Codigo de item pedido invalido!"));
	}
	
	//Por padrao se retornar objeto o ResponseStatus = HttpStatus.OK 
	@GetMapping("{id}")
	public ItemPedido retriveItemPedido(@PathVariable Integer id) {
		
			return this.itemPedidoRepository.findById(id)
											.orElseThrow(() -> new RegraNegocioException("Codigo de item pedido invalido!"));
	}

	@GetMapping
	public List<ItemPedido> retriveAllItemPedidos(@RequestBody ItemPedido itemPedido) {
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(itemPedido, exampleMatcher);
		return this.itemPedidoRepository.findAll(example);
	}
}