package io.github.kassiarsalbuquerque.VendasSpringWeb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.kassiarsalbuquerque.VendasSpringWeb.errors.ApiErros;
import io.github.kassiarsalbuquerque.VendasSpringWeb.exception.RegraNegocioException;

@RestControllerAdvice
public class ApiControllerAdvice {
	// Toda vez q uma excecao personalizada é lancada essa classe trata a excecao(RegraNegocioException). 
	// Para retornar um STATUS e uma msg de erro customizada
	
	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiErros handleRegraNegocioException(RegraNegocioException ex) {
		return new ApiErros(ex.getMessage());
	}
	
	// Trata as excecoes de validacao de campo obrigatorio das classes pedido, produto, cliente e item pedido
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiErros handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getAllErrors()
								.stream()
								.map(erro -> erro.getDefaultMessage())
								.collect(Collectors.toList());
		
		return new ApiErros(errors);
	}
}