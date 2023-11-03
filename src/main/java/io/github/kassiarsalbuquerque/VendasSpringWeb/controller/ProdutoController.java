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

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Produto;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository.IProdutoRepository;
import io.github.kassiarsalbuquerque.VendasSpringWeb.exception.RegraNegocioException;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private IProdutoRepository produtoRepository;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Produto createProduto(@RequestBody @Valid Produto produto) {
		return this.produtoRepository.save(produto);
	}
	
	@PatchMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateProduto(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
		
		this.produtoRepository.findById(id)
								.map(produtoConsultado -> {
									produto.setId(produtoConsultado.getId());
									return this.produtoRepository.save(produto);})
								.orElseThrow(() -> new RegraNegocioException("Codigo de produto invalido!"));
	}
	
	@DeleteMapping(value="{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteProduto(@PathVariable Integer id) {
		
		this.produtoRepository.findById(id)
								.map(produto -> {
									this.produtoRepository.delete(produto);
									return produto;})
								.orElseThrow(() -> new RegraNegocioException("Codigo de produto invalido!"));
	}
	
	//Por padrao se retornar objeto o ResponseStatus = HttpStatus.OK 
	@GetMapping("{id}")
	public Produto retriveProduto(@PathVariable Integer id) {
		
			return this.produtoRepository.findById(id)
											.orElseThrow(() -> new RegraNegocioException("Codigo de produto invalido!"));
	}

	@GetMapping
	public List<Produto> retriveAllProdutos(@RequestBody Produto produto) {
		
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(produto, exampleMatcher);
		return this.produtoRepository.findAll(example);
	}
}
