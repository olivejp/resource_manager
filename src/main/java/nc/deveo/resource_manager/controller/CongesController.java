package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.controller.dto.CongesCreateDto;
import nc.deveo.resource_manager.controller.dto.CongesDto;
import nc.deveo.resource_manager.service.CongesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conges")
public class CongesController {

    private final CongesService service;

    @GetMapping
    public ResponseEntity<Page<CongesDto>> getAll(final Pageable pageable) {
        log.debug("REST request to get all conges");
        final Page<CongesDto> congesPage = service.findAll(pageable);
        return ResponseEntity.ok(congesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongesDto> get(@PathVariable final Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CongesDto> create(@RequestBody @Valid final CongesCreateDto dto) {
        final CongesDto congesCreated = service.save(dto);
        return ResponseEntity.ok(congesCreated);
    }

    @PutMapping
    public ResponseEntity<CongesDto> update(@RequestBody @Valid final CongesCreateDto conges) {
        final CongesDto congesUpdated = service.save(conges);
        return ResponseEntity.ok(congesUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
