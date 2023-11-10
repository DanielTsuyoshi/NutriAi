package br.com.fiap.nutriai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.nutriai.models.UsuarioNutricao;

public interface UsuarioNutricaoRepository extends JpaRepository<UsuarioNutricao, Long> {

    Page<UsuarioNutricao> findByAlgumaPropriedade(String searchTerm, Pageable pageable);

    @Query("SELECT u FROM UsuarioNutricao u WHERE u.algumaPropriedade = :algumaPropriedade")
    UsuarioNutricao findByAlgumaPropriedadeEspecifica(@Param("algumaPropriedade") String algumaPropriedade);
}
