package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Usuario;


public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{

	public Optional<Usuario> findByLogin(String login);
}
