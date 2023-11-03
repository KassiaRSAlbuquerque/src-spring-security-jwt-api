package io.github.kassiarsalbuquerque.VendasSpringWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VendasSpringWebApplication {


//	@Bean
//	public CommandLineRunner init(@Autowired IClienteRepository clienteRepository) {
//		return args ->{
//			System.out.println("Salvando clientes.");
//			clienteRepository.save(new Cliente("Adicionando cliente na mao","14589632187","kassia.regina" ,BCrypt.withDefaults().hashToString(12, "Krgh@2345".toCharArray())));
//			
//			List<Cliente> todosClientes = clienteRepository.findAll();
//			todosClientes.forEach(System.out::println);
//			
//			List<Cliente> clientes = clienteRepository.findByUsuarioLike("kassia.regina");
//			clientes.forEach(System.out::println);
//		}; 
//	}	
	
	public static void main(String[] args) {
		SpringApplication.run(VendasSpringWebApplication.class, args);
	}

}
