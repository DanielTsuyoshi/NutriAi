package br.com.fiap.nutriai.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import br.com.fiap.nutriai.controllers.ConsultaNutricionalController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_NUT_CONSULTA_NUTRICIONAL")
public class ConsultaNutricional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_consulta_nutricional")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cd_paciente")
    private UsuarioNutricao paciente;

    @ManyToOne
    @JoinColumn(name = "cd_nutricionista")
    private NutricionistaNutricao nutricionista;

    private String descricao;

    @Column(name = "dt_consulta")
    private LocalDateTime dataConsulta;

    private boolean realizada;

    @ManyToOne
    @JoinColumn(name = "cd_avaliacao_nutricional")
    private AvaliacaoNutricional avaliacaoNutricional;

    public EntityModel<ConsultaNutricional> toEntityModel(Link... links) {
        return EntityModel.of(
            this,
            links,
            linkTo(methodOn(ConsultaNutricionalController.class).show(id)).withSelfRel(),
            linkTo(methodOn(ConsultaNutricionalController.class).delete(id)).withRel("delete"),
            linkTo(methodOn(ConsultaNutricionalController.class).index(null, Pageable.unpaged())).withRel("all")
        );
    }
}
