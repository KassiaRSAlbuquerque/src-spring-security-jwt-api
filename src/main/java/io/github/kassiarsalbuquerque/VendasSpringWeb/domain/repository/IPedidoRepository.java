package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Cliente;
import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Pedido;

public interface IPedidoRepository extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByCliente(Cliente cliente);
}
