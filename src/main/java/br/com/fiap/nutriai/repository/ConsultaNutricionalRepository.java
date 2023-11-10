package br.com.fiap.nutriai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.nutriai.models.ConsultaNutricional;

public interface ConsultaNutricionalRepository extends JpaRepository<ConsultaNutricional, Long> {

    Page<ConsultaNutricional> findByAlgumaPropriedade(String searchTerm, Pageable pageable);

    @Query("SELECT c FROM ConsultaNutricional c WHERE c.algumaPropriedade = :algumaPropriedade")
    ConsultaNutricional findByAlgumaPropriedadeEspecifica(@Param("algumaPropriedade") String algumaPropriedade);
}
