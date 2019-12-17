package com.levimartines.cursomc;

import com.levimartines.cursomc.enums.EstadoPagamento;
import com.levimartines.cursomc.enums.TipoCliente;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.model.Cidade;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.Endereco;
import com.levimartines.cursomc.model.Estado;
import com.levimartines.cursomc.model.ItemPedido;
import com.levimartines.cursomc.model.Pagamento;
import com.levimartines.cursomc.model.PagamentoComBoleto;
import com.levimartines.cursomc.model.PagamentoComCartao;
import com.levimartines.cursomc.model.Pedido;
import com.levimartines.cursomc.model.Produto;
import com.levimartines.cursomc.repository.CategoriaRepository;
import com.levimartines.cursomc.repository.CidadeRepository;
import com.levimartines.cursomc.repository.ClienteRepository;
import com.levimartines.cursomc.repository.EnderecoRepository;
import com.levimartines.cursomc.repository.EstadoRepository;
import com.levimartines.cursomc.repository.ItemPedidoRepository;
import com.levimartines.cursomc.repository.PagamentoRepository;
import com.levimartines.cursomc.repository.PedidoRepository;
import com.levimartines.cursomc.repository.ProdutoRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria("Escritório");
        Categoria cat3 = new Categoria("Cama mesa e banho");
        Categoria cat4 = new Categoria("Eletrônicos");
        Categoria cat5 = new Categoria("Jardinagem");
        Categoria cat6 = new Categoria("Decoração");
        Categoria cat7 = new Categoria("Perfumaria");

        Produto p1 = new Produto("Computador", 2000.0);
        Produto p2 = new Produto("Impressora", 800.0);
        Produto p3 = new Produto("Mouse", 80.0);
        Produto p4 = new Produto("Mesa de escritório", 300.00);
        Produto p5 = new Produto("Toalha", 50.00);
        Produto p6 = new Produto("Colcha", 200.00);
        Produto p7 = new Produto("TV true color", 1200.00);
        Produto p8 = new Produto("Roçadeira", 800.00);
        Produto p9 = new Produto("Abajour", 100.00);
        Produto p10 = new Produto("Pendente", 180.00);
        Produto p11 = new Produto("Shampoo", 90.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2));
        cat2.getProdutos().add(p3);

        p1.getCategorias().add(cat1);
        p2.getCategorias().add(cat1);
        p3.getCategorias().add(cat2);

        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat6));
        p10.getCategorias().addAll(Arrays.asList(cat6));
        p11.getCategorias().addAll(Arrays.asList(cat7));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = Cidade.builder().nome("Uberlândia").estado(est1).build();
        Cidade c2 = Cidade.builder().nome("São Paulo").estado(est2).build();
        Cidade c3 = Cidade.builder().nome("Campinas").estado(est2).build();

        est1.getCidades().add(c1);
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "19927087039",
            TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("3331125", "5555555"));

        Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220854",
            cli1, c1);
        Endereco end2 = new Endereco(null, "Av Matos", "105", "Sala 800", "Centro", "18777012",
            cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

        clienteRepository.save(cli1);
        enderecoRepository.saveAll(Arrays.asList(end1, end2));

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6L);
        ped1.setPagamento(pagto1);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,
            sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1L, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2L, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1L, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

    }
}
