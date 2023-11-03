package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Produto;

public interface IProdutoRepository extends JpaRepository<Produto, Integer> {

}
