package io.github.kassiarsalbuquerque.VendasSpringWeb.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Pedido;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.enums.StatusPedido;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IClienteRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IPedidoRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.exception.RegraNegocioException;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private IPedidoRepository pedidoRepository;
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Transactional
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Pedido createPedido(@RequestBody @Valid Pedido pedido) {
		
		this.clienteRepository.findById(pedido.getCliente().getId()).orElseThrow(() -> new RegraNegocioException("Codigo de cliente invalido!"));
		pedido.setTotal(Long.parseLong("0"));
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setStatus(StatusPedido.REALIZADO);
		
		return this.pedidoRepository.save(pedido);
	}
	
	@PatchMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updatePedido(@PathVariable Integer id, @RequestBody @Valid Pedido pedido) {
		
		this.pedidoRepository.findById(id)
								.map(pedidoConsultado -> {
									pedido.setId(pedidoConsultado.getId());
									return this.pedidoRepository.save(pedido);})
								.orElseThrow(() -> new RegraNegocioException("Codigo de pedido invalido!"));
	}
	
	@DeleteMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePedido(@PathVariable Integer id) {
		
		this.pedidoRepository.findById(id)
								.map(pedido -> {
									this.pedidoRepository.delete(pedido);
									return pedido;})
								.orElseThrow(() -> new RegraNegocioException("Codigo de pedido invalido!"));
	}
	
	//Por padrao se retornar objeto o ResponseStatus = HttpStatus.OK 
	@GetMapping("{id}")
	public Pedido retrivePedido(@PathVariable Integer id) {
		
			return this.pedidoRepository.findById(id)
											.orElseThrow(() -> new RegraNegocioException("Codigo de pedido invalido!"));
	}

	@GetMapping
	public List<Pedido> retriveAllPedidos(@RequestBody Pedido pedido) {
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(pedido, exampleMatcher);
		return this.pedidoRepository.findAll(example);
	}
}

