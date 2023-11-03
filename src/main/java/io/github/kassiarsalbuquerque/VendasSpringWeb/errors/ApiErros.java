package io.github.kassiarsalbuquerque.VendasSpringWeb.errors;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErros {

	@Getter
	private List<String> errors;
	
	public ApiErros(String mensagemError) {
		this.errors = Arrays.asList(mensagemError);
	}

	public ApiErros(List<String> errors) {
		super();
		this.errors = errors;
	}
}