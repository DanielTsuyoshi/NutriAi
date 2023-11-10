package br.com.fiap.nutriai.controllers;

import br.com.fiap.nutriai.models.PlanoNutricao;
import br.com.fiap.nutriai.repository.PlanoNutricaoRepository;
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
@RequestMapping("/api/planos-nutricao")
public class PlanoNutricaoController {

    @Autowired
    private PlanoNutricaoRepository planoNutricaoRepository;

    @Autowired
    private PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> list(@RequestParam(required = false) String searchTerm, @PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<PlanoNutricao> planosNutricao = (searchTerm == null) ?
                    planoNutricaoRepository.findAll(pageable) :
                    planoNutricaoRepository.findByAlgumaPropriedade(searchTerm, pageable);

            return assembler.toModel(planosNutricao.map(PlanoNutricao::toEntityModel));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @GetMapping("{id}")
    public EntityModel<PlanoNutricao> details(@PathVariable Long id) {
        try {
            PlanoNutricao planoNutricao = findByPlanoNutricao(id);
            return planoNutricao.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> createPlanoNutricao(@RequestBody @Valid PlanoNutricao planoNutricao) {
        try {
            planoNutricaoRepository.save(planoNutricao);
            return ResponseEntity
                    .created(planoNutricao.toEntityModel().getRequiredLink("self").toUri())
                    .body(planoNutricao.toEntityModel());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePlanoNutricao(@PathVariable Long id) {
        try {
            planoNutricaoRepository.delete(findByPlanoNutricao(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PutMapping("{id}")
    public EntityModel<PlanoNutricao> updatePlanoNutricao(@PathVariable Long id, @RequestBody PlanoNutricao planoNutricao) {
        try {
            findByPlanoNutricao(id);
            planoNutricao.setId(id);
            planoNutricaoRepository.save(planoNutricao);
            return planoNutricao.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    private PlanoNutricao findByPlanoNutricao(Long id) {
        return planoNutricaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PlanoNutricao not found"));
    }
}
