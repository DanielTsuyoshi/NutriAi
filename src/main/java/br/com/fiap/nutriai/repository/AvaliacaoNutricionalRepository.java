package br.com.fiap.nutriai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.nutriai.models.AvaliacaoNutricional;

public interface AvaliacaoNutricionalRepository extends JpaRepository<AvaliacaoNutricional, Long> {

    Page<AvaliacaoNutricional> findByAlgumaPropriedade(String searchTerm, Pageable pageable);

    @Query("SELECT a FROM AvaliacaoNutricional a WHERE a.algumaPropriedade = :algumaPropriedade")
    AvaliacaoNutricional findByAlgumaPropriedadeEspecifica(@Param("algumaPropriedade") String algumaPropriedade);
}
