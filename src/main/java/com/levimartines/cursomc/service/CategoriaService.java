package com.levimartines.cursomc.service;

import com.levimartines.cursomc.bean.CategoriaBean;
import com.levimartines.cursomc.exceptions.DataIntegrityException;
import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import com.levimartines.cursomc.model.Categoria;
import com.levimartines.cursomc.repository.CategoriaRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(
        CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy,
        String direction) {
        PageRequest pageRequest = PageRequest
            .of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() ->
            new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                + Categoria.class));
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria save(Categoria obj) {
        obj.setId(null);
        return categoriaRepository.save(obj);
    }

    public void update(Categoria obj) {
        Categoria newObj = findById(obj.getId());
        updateData(newObj, obj);
        categoriaRepository.save(newObj);
    }

    public void delete(Long id) {
        try {
            findById(id);
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                "Não é possível excluir uma Categoria que possui Produtos");
        }
    }

    public Categoria fromBean(CategoriaBean bean) {
        return new Categoria(bean.getId(), bean.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }
}
