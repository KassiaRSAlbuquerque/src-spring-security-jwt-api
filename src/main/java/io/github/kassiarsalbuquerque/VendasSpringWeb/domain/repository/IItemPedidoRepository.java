package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.ItemPedido;

public interface IItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{

}
