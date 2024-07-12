package com.alura.LiterAlura;

import com.alura.LiterAlura.principal.Principal;
import com.alura.LiterAlura.repository.LibroRepository;
import com.alura.LiterAlura.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repositoryLibro;
	@Autowired
	private PersonaRepository repositoryPersona;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryLibro,repositoryPersona);
		principal.mostrarMenu();
	}
}
