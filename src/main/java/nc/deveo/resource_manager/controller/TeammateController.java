package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.domain.Teammate;
import nc.deveo.resource_manager.repository.TeammateRepository;
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
public class TeammateController {

    private final TeammateRepository repository;

    @GetMapping("/teamate")
    public ResponseEntity<Page<Teammate>> getAll(final Pageable pageable) {
        log.debug("REST request to get all teamate");
        final Page<Teammate> teamatePage = repository.findAll(pageable);
        return ResponseEntity.ok().body(teamatePage);
    }

    @GetMapping("/teamate/{id}")
    public ResponseEntity<Teammate> get(@PathVariable final Long id) {
        return repository.findById(id)
                .map(teamate -> ResponseEntity.ok().body(teamate))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/teamate")
    public ResponseEntity<Teammate> create(@RequestBody @Valid Teammate teammateToCreate) {
        final Teammate teammateCreated = repository.save(teammateToCreate);
        return ResponseEntity.ok().body(teammateCreated);
    }

    @PutMapping("/teamate/{id}")
    public ResponseEntity<Teammate> update(@PathVariable Long id,
                                           @RequestBody @Valid Teammate teammateToUpdate) {
        final Teammate teammateUpdated = repository.save(teammateToUpdate);
        return ResponseEntity.ok().body(teammateUpdated);
    }

    @DeleteMapping("/teamate/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
