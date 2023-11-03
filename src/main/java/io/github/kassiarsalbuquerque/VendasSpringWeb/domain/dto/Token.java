package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

	private String login;
	private String token;
}
