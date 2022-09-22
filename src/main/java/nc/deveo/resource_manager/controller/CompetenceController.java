package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.domain.Competence;
import nc.deveo.resource_manager.repository.CompetenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/competence")
public class CompetenceController {

    private final CompetenceRepository repository;

    @GetMapping
    public ResponseEntity<Page<Competence>> getAll(final Pageable pageable) {
        log.debug("REST request to get all competence");
        final Page<Competence> competencePage = repository.findAll(pageable);
        return ResponseEntity.ok().body(competencePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competence> get(@PathVariable final Long id) {
        return repository.findById(id)
                .map(competence -> ResponseEntity.ok().body(competence))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Competence> create(@RequestBody @Valid Competence competenceToCreate) {
        final Competence competenceCreated = repository.save(competenceToCreate);
        return ResponseEntity.ok().body(competenceCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competence> update(@PathVariable Long id,
                                             @RequestBody @Valid Competence competenceToUpdate) {
        final Competence competenceUpdated = repository.save(competenceToUpdate);
        return ResponseEntity.ok().body(competenceUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
