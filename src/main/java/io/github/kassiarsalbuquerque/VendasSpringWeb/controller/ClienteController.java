package io.github.kassiarsalbuquerque.VendasSpringWeb.controller;

import java.util.List;

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

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Cliente;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IClienteRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.exception.RegraNegocioException;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private IClienteRepository clienteRepository;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cliente createCliente(@RequestBody @Valid Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}

	@PatchMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateProduto(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
		
		this.clienteRepository.findById(id)
								.map(clienteConsultado -> {
									cliente.setId(clienteConsultado.getId());
									return this.clienteRepository.save(cliente);})
								.orElseThrow(() -> new RegraNegocioException("Codigo de cliente invalido!"));
	}

	@DeleteMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteCliente(@PathVariable Integer id) {
		
		this.clienteRepository.findById(id)
								.map(cliente -> {
									this.clienteRepository.delete(cliente);
									return cliente;})
								.orElseThrow(() -> new RegraNegocioException("Codigo de cliente invalido!"));
	}


	//Por padrao se retornar objeto o ResponseStatus = HttpStatus.OK 
	@GetMapping("{id}")
	public Cliente retriveCliente(@PathVariable Integer id) {

		return this.clienteRepository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Codigo de cliente invalido!"));
	}

	@GetMapping
	public List<Cliente> retriveAllClientes(@RequestBody Cliente cliente) {
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(cliente, exampleMatcher);
		return this.clienteRepository.findAll(example);
	}
}