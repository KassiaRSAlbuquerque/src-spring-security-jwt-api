package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Credenciais {

	private String login;
	private String senha;
}
