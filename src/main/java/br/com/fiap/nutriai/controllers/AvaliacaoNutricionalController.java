package br.com.fiap.nutriai.controllers;

import br.com.fiap.nutriai.models.AvaliacaoNutricional;
import br.com.fiap.nutriai.repository.AvaliacaoNutricionalRepository;
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
@RequestMapping("/api/avaliacoes-nutricionais")
public class AvaliacaoNutricionalController {

    @Autowired
    private AvaliacaoNutricionalRepository avaliacaoNutricionalRepository;

    @Autowired
    private PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> list(@RequestParam(required = false) String searchTerm, @PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<AvaliacaoNutricional> avaliacoes = (searchTerm == null) ?
                    avaliacaoNutricionalRepository.findAll(pageable) :
                    avaliacaoNutricionalRepository.findByAlgumaPropriedade(searchTerm, pageable);

            return assembler.toModel(avaliacoes.map(AvaliacaoNutricional::toEntityModel));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @GetMapping("{id}")
    public EntityModel<AvaliacaoNutricional> details(@PathVariable Long id) {
        try {
            AvaliacaoNutricional avaliacao = findByAvaliacaoNutricional(id);
            return avaliacao.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> createAvaliacaoNutricional(@RequestBody @Valid AvaliacaoNutricional avaliacaoNutricional) {
        try {
            avaliacaoNutricionalRepository.save(avaliacaoNutricional);
            return ResponseEntity
                    .created(avaliacaoNutricional.toEntityModel().getRequiredLink("self").toUri())
                    .body(avaliacaoNutricional.toEntityModel());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAvaliacaoNutricional(@PathVariable Long id) {
        try {
            avaliacaoNutricionalRepository.delete(findByAvaliacaoNutricional(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PutMapping("{id}")
    public EntityModel<AvaliacaoNutricional> updateAvaliacaoNutricional(@PathVariable Long id, @RequestBody AvaliacaoNutricional avaliacaoNutricional) {
        try {
            findByAvaliacaoNutricional(id);
            avaliacaoNutricional.setId(id);
            avaliacaoNutricionalRepository.save(avaliacaoNutricional);
            return avaliacaoNutricional.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    private AvaliacaoNutricional findByAvaliacaoNutricional(Long id) {
        return avaliacaoNutricionalRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AvaliacaoNutricional not found"));
    }
}
