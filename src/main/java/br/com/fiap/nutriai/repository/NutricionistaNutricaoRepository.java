package br.com.fiap.nutriai.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.nutriai.models.NutricionistaNutricao;

public interface NutricionistaNutricaoRepository extends JpaRepository<NutricionistaNutricao, Long> {

    Page<NutricionistaNutricao> findByAlgumaPropriedade(String searchTerm, Pageable pageable);

    @Query("SELECT n FROM NutricionistaNutricao n WHERE n.algumaPropriedade = :algumaPropriedade")
    NutricionistaNutricao findByAlgumaPropriedadeEspecifica(@Param("algumaPropriedade") String algumaPropriedade);
}
