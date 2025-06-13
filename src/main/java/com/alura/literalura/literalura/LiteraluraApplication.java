package com.alura.literalura.literalura;

import com.alura.literalura.literalura.principal.Principal;
import com.alura.literalura.literalura.repositories.AutorRepository;
import com.alura.literalura.literalura.repositories.LibroRepository;
import com.alura.literalura.literalura.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository repositorioAutor;

	@Autowired
	private LibroRepository repositorioLibro;

	@Autowired
	private LibroService libroService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args)throws Exception{
		Principal principal = new Principal(repositorioAutor, repositorioLibro, libroService);
		principal.mostrarMenu();

	}

}
