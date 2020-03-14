package com.levimartines.cursomc.service;

import com.levimartines.cursomc.bean.ClienteBean;
import com.levimartines.cursomc.bean.ClienteNewBean;
import com.levimartines.cursomc.enums.Perfil;
import com.levimartines.cursomc.enums.TipoCliente;
import com.levimartines.cursomc.exceptions.AuthorizationException;
import com.levimartines.cursomc.exceptions.DataIntegrityException;
import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import com.levimartines.cursomc.model.Cidade;
import com.levimartines.cursomc.model.Cliente;
import com.levimartines.cursomc.model.Endereco;
import com.levimartines.cursomc.repository.ClienteRepository;
import com.levimartines.cursomc.repository.EnderecoRepository;
import com.levimartines.cursomc.security.CustomUserDetails;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final S3Service s3Service;
    private final ImageService imageService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${img.prefix.client.profile}")
    private String imagePrefix;

    @Value("${img.profile.size}")
    private String imageSize;

    public Cliente findById(Long id) {
        CustomUserDetails userDetails = UserService.authenticated();
        if (userDetails == null || !userDetails.hasRole(Perfil.ADMIN) && !id
            .equals(userDetails.getId())) {
            throw new AuthorizationException("Acesso negado");
        }
        return clienteRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Cliente.class));
    }

    public Cliente findByEmail(String email) {
        CustomUserDetails userDetails = UserService.authenticated();
        if (userDetails == null || !userDetails.hasRole(Perfil.ADMIN) && !email
            .equals(userDetails.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }
        return clienteRepository.findByEmail(email);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy,
        String direction) {
        PageRequest pageRequest = PageRequest
            .of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente save(Cliente obj) {
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public void update(Cliente obj) {
        Cliente newObj = findById(obj.getId());
        updateData(newObj, obj);
        clienteRepository.save(newObj);
    }

    public void delete(Long id) {
        try {
            findById(id);
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                "Não é possível excluir um Cliente que possui Entidades relacionadas");
        }
    }

    public URI uploadProfilePicture(MultipartFile file) {
        CustomUserDetails userDetails = UserService.authenticated();
        if (userDetails == null) {
            throw new AuthorizationException("Acesso negado");
        }
        BufferedImage jpgImage = imageService.getJpgFromFile(file);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, Integer.parseInt(imageSize));
        String fileName = imagePrefix + userDetails.getId() + ".jpg";
        return s3Service
            .uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }

    public Cliente fromBean(ClienteBean bean) {
        return new Cliente(bean.getId(), bean.getNome(), bean.getEmail());
    }

    public Cliente fromBean(ClienteNewBean bean) {
        Cliente cliente = new Cliente(null, bean.getNome(), bean.getEmail(), bean.getCpfOuCnpj(),
            TipoCliente
                .toEnum(bean.getTipo()), passwordEncoder.encode(bean.getSenha()));

        Endereco end = new Endereco();
        end.setLogradouro(bean.getLogradouro());
        end.setNumero(bean.getNumero());
        end.setComplemento(bean.getComplemento());
        end.setBairro(bean.getBairro());
        end.setCep(bean.getCep());
        end.setCidade(new Cidade(bean.getCidadeId(), null, null));

        cliente.getEnderecos().add(end);
        cliente.getTelefones().add(bean.getTelefone1());
        if (bean.getTelefone2() != null) {
            cliente.getTelefones().add(bean.getTelefone2());
        }
        if (bean.getTelefone3() != null) {
            cliente.getTelefones().add(bean.getTelefone3());
        }
        return cliente;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
