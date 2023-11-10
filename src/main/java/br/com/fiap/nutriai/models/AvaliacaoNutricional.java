package br.com.fiap.nutriai.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import br.com.fiap.nutriai.controllers.AvaliacaoNutricionalController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_NUT_AVALIACAO_NUTRICIONAL")
public class AvaliacaoNutricional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_avaliacao_nutricional")
    private Long id;

    @NotBlank(message = "O resumo é obrigatório")
    @Column(name = "ds_resumo")
    private String resumo;

    @ElementCollection
    @CollectionTable(name = "T_NUT_RESULTADOS_AVALIACAO", joinColumns = @JoinColumn(name = "cd_avaliacao_nutricional"))
    @MapKeyColumn(name = "ds_nome")
    @Column(name = "ds_valor")
    private Map<String, String> resultados;

    @ManyToOne
    @JoinColumn(name = "cd_nutricionista")
    private NutricionistaNutricao nutricionista;

    @ManyToOne
    @JoinColumn(name = "cd_paciente")
    private UsuarioNutricao paciente;

    public EntityModel<AvaliacaoNutricional> toEntityModel(Link... links) {
        return EntityModel.of(
            this,
            links,
            linkTo(methodOn(AvaliacaoNutricionalController.class).show(id)).withSelfRel(),
            linkTo(methodOn(AvaliacaoNutricionalController.class).delete(id)).withRel("delete"),
            linkTo(methodOn(AvaliacaoNutricionalController.class).index(null, Pageable.unpaged())).withRel("all")
        );
    }
}
