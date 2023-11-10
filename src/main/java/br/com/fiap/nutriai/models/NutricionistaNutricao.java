package br.com.fiap.nutriai.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import br.com.fiap.nutriai.controllers.NutricionistaNutricaoController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_NUT_NUTRICIONISTA_NUTRICAO")
public class NutricionistaNutricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_nutricionista_nutricao")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nm_nutricionista")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Column(name = "ds_email")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(name = "nr_telefone")
    private String telefone;

    @NotBlank(message = "O endereco é obrigatório")
    @Column(name = "ds_endereco")
    private String endereco;

    @OneToMany(mappedBy = "nutricionista")
    private List<AvaliacaoNutricional> avaliacoesNutricionais;

    @OneToMany(mappedBy = "nutricionista")
    private List<ConsultaNutricional> consultasNutricionais;

    public EntityModel<NutricionistaNutricao> toEntityModel(Link... links) {
        return EntityModel.of(
            this,
            links,
            linkTo(methodOn(NutricionistaNutricaoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(NutricionistaNutricaoController.class).delete(id)).withRel("delete"),
            linkTo(methodOn(NutricionistaNutricaoController.class).index(null, Pageable.unpaged())).withRel("all")
        );
    }
}
