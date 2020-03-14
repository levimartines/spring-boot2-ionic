package com.levimartines.cursomc.repository;

import com.levimartines.cursomc.model.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    List<Estado> findAllByNomeTrueOrderByNome();
}
