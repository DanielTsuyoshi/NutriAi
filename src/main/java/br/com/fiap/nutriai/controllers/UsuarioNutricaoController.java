package br.com.fiap.nutriai.controllers;

import br.com.fiap.nutriai.models.UsuarioNutricao;
import br.com.fiap.nutriai.repository.UsuarioNutricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios-nutricao")
public class UsuarioNutricaoController {

    @Autowired
    private UsuarioNutricaoRepository usuarioNutricaoRepository;

    @Autowired
    private PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> list(@RequestParam(required = false) String searchTerm, @PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<UsuarioNutricao> usuarios = (searchTerm == null) ?
                    usuarioNutricaoRepository.findAll(pageable) :
                    usuarioNutricaoRepository.findByAlgumaPropriedade(searchTerm, pageable);

            return assembler.toModel(usuarios.map(UsuarioNutricao::toEntityModel));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @GetMapping("{id}")
    public EntityModel<UsuarioNutricao> details(@PathVariable Long id) {
        try {
            UsuarioNutricao usuario = findByUsuarioNutricao(id);
            return usuario.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> createUsuarioNutricao(@RequestBody @Valid UsuarioNutricao usuarioNutricao) {
        try {
            usuarioNutricaoRepository.save(usuarioNutricao);
            return ResponseEntity
                    .created(usuarioNutricao.toEntityModel().getRequiredLink("self").toUri())
                    .body(usuarioNutricao.toEntityModel());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUsuarioNutricao(@PathVariable Long id) {
        try {
            usuarioNutricaoRepository.delete(findByUsuarioNutricao(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PutMapping("{id}")
    public EntityModel<UsuarioNutricao> updateUsuarioNutricao(@PathVariable Long id, @RequestBody UsuarioNutricao usuarioNutricao) {
        try {
            findByUsuarioNutricao(id);
            usuarioNutricao.setId(id);
            usuarioNutricaoRepository.save(usuarioNutricao);
            return usuarioNutricao.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    private UsuarioNutricao findByUsuarioNutricao(Long id) {
        return usuarioNutricaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UsuarioNutricao not found"));
    }
}
