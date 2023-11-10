package br.com.fiap.nutriai.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.fiap.nutriai.controllers.PlanoNutricaoController;
import br.com.fiap.nutriai.controllers.UsuarioNutricaoController;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_NUT_USUARIO_NUTRICAO")
public class UsuarioNutricao implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario_nutricao")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50)
    @Column(name = "nm_usuario")
    private String nome;

    @NotBlank(message="O email é obrigatório")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(name = "ds_email")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8)
    @Column(name = "ds_senha")
    private String senha;

    @Size(min = 8)
    @Transient
    private String confirmarSenha;

    @NotNull
    @Column(name = "dt_nascimento")
    private LocalDate data;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-\\d{4}$")
    @Column(name = "nr_telefone")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "cd_plano_nutricao")
    private PlanoNutricao plano;

    public EntityModel<UsuarioNutricao> toEntityModel(Link... links) {
        return EntityModel.of(
            this,
            links,
            linkTo(methodOn(UsuarioNutricaoController.class).show(id)).withSelfRel(),
            linkTo(methodOn(UsuarioNutricaoController.class).delete(id)).withRel("delete"),
            linkTo(methodOn(UsuarioNutricaoController.class).index(null, Pageable.unpaged())).withRel("all"),
            linkTo(methodOn(PlanoNutricaoController.class).show(this.getPlano().getId())).withRel("plano")
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
