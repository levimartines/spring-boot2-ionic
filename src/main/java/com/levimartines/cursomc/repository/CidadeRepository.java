package com.levimartines.cursomc.repository;

import com.levimartines.cursomc.model.Cidade;
import com.levimartines.cursomc.model.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    List<Cidade> findAllByEstadoOrderByNome(Estado estado);
}
