package com.levimartines.cursomc;

import com.levimartines.cursomc.enums.TipoCliente;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Cidade;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.Endereco;
import com.levimartines.cursomc.model.Estado;
import com.levimartines.cursomc.model.Produto;
import com.levimartines.cursomc.repositories.CategoriaRepository;
import com.levimartines.cursomc.repositories.CidadeRepository;
import com.levimartines.cursomc.repositories.ClienteRepository;
import com.levimartines.cursomc.repositories.EnderecoRepository;
import com.levimartines.cursomc.repositories.EstadoRepository;
import com.levimartines.cursomc.repositories.ProdutoRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

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

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = Cidade.builder().nome("Uberlândia").estado(est1).build();
        Cidade c2 = Cidade.builder().nome("São Paulo").estado(est2).build();
        Cidade c3 = Cidade.builder().nome("Campinas").estado(est2).build();

        est1.getCidades().add(c1);
        est2.getCidades().addAll(Arrays.asList(c2,c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente("Maria Silva", "19927087039", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("3331125", "5555555"));

        Endereco end1 = new Endereco(null,"Rua Flores", "300", "Apto 303", "Jardim", "38220854", cli1, c1);
        Endereco end2 = new Endereco(null,"Av Matos", "105", "Sala 800", "Centro", "18777012", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(end1,end2));

        clienteRepository.save(cli1);
        enderecoRepository.saveAll(Arrays.asList(end1,end2));

    }
}
