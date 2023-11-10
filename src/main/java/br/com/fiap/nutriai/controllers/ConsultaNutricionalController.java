package br.com.fiap.nutriai.controllers;

import br.com.fiap.nutriai.models.ConsultaNutricional;
import br.com.fiap.nutriai.repository.ConsultaNutricionalRepository;
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
@RequestMapping("/api/consultas-nutricionais")
public class ConsultaNutricionalController {

    @Autowired
    private ConsultaNutricionalRepository consultaNutricionalRepository;

    @Autowired
    private PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> list(@RequestParam(required = false) String searchTerm, @PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<ConsultaNutricional> consultas = (searchTerm == null) ?
                    consultaNutricionalRepository.findAll(pageable) :
                    consultaNutricionalRepository.findByAlgumaPropriedade(searchTerm, pageable);

            return assembler.toModel(consultas.map(ConsultaNutricional::toEntityModel));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @GetMapping("{id}")
    public EntityModel<ConsultaNutricional> details(@PathVariable Long id) {
        try {
            ConsultaNutricional consulta = findByConsultaNutricional(id);
            return consulta.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> createConsultaNutricional(@RequestBody @Valid ConsultaNutricional consultaNutricional) {
        try {
            consultaNutricionalRepository.save(consultaNutricional);
            return ResponseEntity
                    .created(consultaNutricional.toEntityModel().getRequiredLink("self").toUri())
                    .body(consultaNutricional.toEntityModel());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteConsultaNutricional(@PathVariable Long id) {
        try {
            consultaNutricionalRepository.delete(findByConsultaNutricional(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @PutMapping("{id}")
    public EntityModel<ConsultaNutricional> updateConsultaNutricional(@PathVariable Long id, @RequestBody ConsultaNutricional consultaNutricional) {
        try {
            findByConsultaNutricional(id);
            consultaNutricional.setId(id);
            consultaNutricionalRepository.save(consultaNutricional);
            return consultaNutricional.toEntityModel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    private ConsultaNutricional findByConsultaNutricional(Long id) {
        return consultaNutricionalRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ConsultaNutricional not found"));
    }
}
