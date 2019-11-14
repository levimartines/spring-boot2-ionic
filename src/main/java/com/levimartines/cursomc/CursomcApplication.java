package com.levimartines.cursomc;

import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Produto;
import com.levimartines.cursomc.repositories.CategoriaRepository;
import com.levimartines.cursomc.repositories.ProdutoRepository;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    private CategoriaRepository categoriaRepository;
    private ProdutoRepository produtoRepository;

    public CursomcApplication(
        CategoriaRepository categoriaRepository,
        ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria("Escritório");

        Produto prod1 = new Produto("Computador",2000.0);
        Produto prod2 = new Produto("Impressora", 800.0);
        Produto prod3 = new Produto("Mouse", 80.0);

        cat1.getProdutos().addAll(Arrays.asList(prod1, prod2));
        cat2.getProdutos().add(prod3);

        prod1.getCategorias().add(cat1);
        prod2.getCategorias().add(cat1);
        prod3.getCategorias().add(cat2);

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3));
    }
}
