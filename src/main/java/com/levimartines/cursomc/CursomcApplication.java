package com.levimartines.cursomc;

import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.repositories.CategoriaRepository;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	private CategoriaRepository categoriaRepository;

	public CursomcApplication(
		CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}
}
