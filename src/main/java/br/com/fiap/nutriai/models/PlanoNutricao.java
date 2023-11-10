package br.com.fiap.nutriai.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import br.com.fiap.nutriai.controllers.PlanoNutricaoController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_NUT_PLANO_NUTRICAO")
public class PlanoNutricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_plano_nutricao")
    private Long id;

    @NotBlank(message = "O tipo de plano é obrigatório")
    @Column(name = "ds_tipo_plano")
    private String tipoPlano;

    @Column(name = "vl_plano_mensal")
    private BigDecimal planoMensal;

    @Column(name = "vl_plano_anual")
    private BigDecimal planoAnual;

    public EntityModel<PlanoNutricao> toEntityModel(Link... links) {
        return EntityModel.of(
            this,
            links,
            linkTo(methodOn(PlanoNutricaoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(PlanoNutricaoController.class).delete(id)).withRel("delete"),
            linkTo(methodOn(PlanoNutricaoController.class).index(null, Pageable.unpaged())).withRel("all")
        );
    }
}
