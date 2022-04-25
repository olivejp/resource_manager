package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.domain.ContratTravail;
import nc.deveo.resource_manager.repository.ContratTravailRepository;
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
@RequestMapping("/api")
public class ContratTravailController {

    private final ContratTravailRepository repository;

    @GetMapping("/contrat-travail")
    public ResponseEntity<Page<ContratTravail>> getAll(final Pageable pageable) {
        log.debug("REST request to get all competence");
        final Page<ContratTravail> competencePage = repository.findAll(pageable);
        return ResponseEntity.ok().body(competencePage);
    }

    @GetMapping("/contrat-travail/{id}")
    public ResponseEntity<ContratTravail> get(@PathVariable final Long id) {
        return repository.findById(id)
                .map(competence -> ResponseEntity.ok().body(competence))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/contrat-travail")
    public ResponseEntity<ContratTravail> create(@RequestBody @Valid ContratTravail competenceToCreate) {
        final ContratTravail competenceCreated = repository.save(competenceToCreate);
        return ResponseEntity.ok().body(competenceCreated);
    }

    @PutMapping("/contrat-travail/{id}")
    public ResponseEntity<ContratTravail> update(@PathVariable Long id,
                                                 @RequestBody @Valid ContratTravail competenceToUpdate) {
        final ContratTravail competenceUpdated = repository.save(competenceToUpdate);
        return ResponseEntity.ok().body(competenceUpdated);
    }

    @DeleteMapping("/contrat-travail/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
