package br.com.fiap.nutriai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.nutriai.models.PlanoNutricao;

public interface PlanoNutricaoRepository extends JpaRepository<PlanoNutricao, Long> {

    Page<PlanoNutricao> findByAlgumaPropriedade(String searchTerm, Pageable pageable);

    @Query("SELECT p FROM PlanoNutricao p WHERE p.algumaPropriedade = :algumaPropriedade")
    PlanoNutricao findByAlgumaPropriedadeEspecifica(@Param("algumaPropriedade") String algumaPropriedade);
}
