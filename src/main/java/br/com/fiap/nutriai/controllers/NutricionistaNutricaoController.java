package br.com.fiap.nutriai.controllers;

import br.com.fiap.nutriai.models.NutricionistaNutricao;
import br.com.fiap.nutriai.repository.NutricionistaNutricaoRepository;
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
@RequestMapping("/api/nutricionistas-nutricao")
public class NutricionistaNutricaoController {

    @Autowired
    private NutricionistaNutricaoRepository nutricionistaNutricaoRepository;

    @Autowired
    private PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> list(@RequestParam(required = false) String searchTerm, @PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<NutricionistaNutricao> nutricionistasNutricao = (searchTerm == null) ?
                    nutricionistaNutricaoRepository.findAll(pageable) :
                    nutricionistaNutricaoRepository.findByAlgumaPropriedade(searchTerm, pageable);

            return assembler.toModel(nutricionistasNutricao.map(NutricionistaNutricao::toEntityModel));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @GetMapping("{id}")
    public EntityModel<NutricionistaNutricao> details(@PathVariable Long id) {
        try {
            NutricionistaNutricao nutricionistaNutricao = findByNutricionistaNutricao(id);
            return nutricionistaNutricao.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> createNutricionistaNutricao(@RequestBody @Valid NutricionistaNutricao nutricionistaNutricao) {
        try {
            nutricionistaNutricaoRepository.save(nutricionistaNutricao);
            return ResponseEntity
                    .created(nutricionistaNutricao.toEntityModel().getRequiredLink("self").toUri())
                    .body(nutricionistaNutricao.toEntityModel());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNutricionistaNutricao(@PathVariable Long id) {
        try {
            nutricionistaNutricaoRepository.delete(findByNutricionistaNutricao(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PutMapping("{id}")
    public EntityModel<NutricionistaNutricao> updateNutricionistaNutricao(@PathVariable Long id, @RequestBody NutricionistaNutricao nutricionistaNutricao) {
        try {
            findByNutricionistaNutricao(id);
            nutricionistaNutricao.setId(id);
            nutricionistaNutricaoRepository.save(nutricionistaNutricao);
            return nutricionistaNutricao.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    private NutricionistaNutricao findByNutricionistaNutricao(Long id) {
        return nutricionistaNutricaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NutricionistaNutricao not found"));
    }
}
