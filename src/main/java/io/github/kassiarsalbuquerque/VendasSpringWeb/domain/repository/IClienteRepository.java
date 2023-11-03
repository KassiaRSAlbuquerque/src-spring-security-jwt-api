package io.github.kassiarsalbuquerque.VendasSpringWeb.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.github.kassiarsalbuquerque.VendasSpringWeb.domain.entity.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer>{

	//POR CONVENCAO O JPA REPOSITORY ENTENDE AS QUERY SIMPLES COM BUSCAR DE ATRIBUTOS DA ENTIDADE PARA OPERACOES COMO:
	// findBy
	// findOneBy
	// existsBy
	public List<Cliente> findByNomeLike(String nome);
	public List<Cliente> findByNomeLikeOrIdOrderById(String nome, Integer id);
	public Cliente findOneByCpf(String cpf);
	public boolean existsByCpf(String cpf);

	
	//O JPA REPOSITORY PERMITE A CUSTOMIZACAO DE QUERY USANDO HQL
	@Query(value = "select c from Cliente c where c.nome like :nome and c.cpf like :cpf")
	public List<Cliente> encontrarPorNomeECpfHQL(@Param("nome") String nome, @Param("cpf") String cpf);
	
	@Modifying
	@Transactional
	@Query(value = "delete from Cliente c where c.nome like :nome")
	public void removendoPeloNomeHQL(@Param("nome") String nome);
	
	
	//O JPA REPOSITORY PERMITE A CUSTOMIZACAO DE QUERY USANDO CONSULTA SQL NATIVA
	@Query(value = "select * from cliente c where c.nome like :nome and c.cpf like :cpf", nativeQuery = true)
	public List<Cliente> encontrarPorNomeECpfNativo(@Param("nome") String nome, @Param("cpf") String cpf);
	
	@Modifying
	@Transactional
	@Query(value = "delete from cliente c where c.nome like :nome", nativeQuery = true)
	public void removendoPeloNomeNativo(@Param("nome") String nome);
}
