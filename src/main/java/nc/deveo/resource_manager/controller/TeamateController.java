package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.domain.Teamate;
import nc.deveo.resource_manager.repository.TeamateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamateController {

    private final TeamateRepository repository;

    @GetMapping("/teamate")
    public ResponseEntity<Page<Teamate>> getAll(final Pageable pageable) {
        log.debug("REST request to get all teamate");
        final Page<Teamate> teamatePage = repository.findAll(pageable);
        return ResponseEntity.ok().body(teamatePage);
    }

    @GetMapping("/teamate/{id}")
    public ResponseEntity<Teamate> get(@PathVariable final Long id) {
        return repository.findById(id)
                .map(teamate -> ResponseEntity.ok().body(teamate))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/teamate")
    public ResponseEntity<Teamate> create(@RequestBody @Valid Teamate teamateToCreate) {
        if (teamateToCreate.getListDocument() != null && !teamateToCreate.getListDocument().isEmpty()) {
            teamateToCreate.getListDocument().forEach(document -> document.setTeamate(teamateToCreate));
        }
        final Teamate teamateCreated = repository.save(teamateToCreate);
        return ResponseEntity.ok().body(teamateCreated);
    }

    @PutMapping("/teamate/{id}")
    public ResponseEntity<Teamate> update(@PathVariable Long id,
                                          @RequestBody @Valid Teamate teamateToUpdate) {
        if (teamateToUpdate.getListDocument() != null && !teamateToUpdate.getListDocument().isEmpty()) {
            teamateToUpdate.getListDocument().forEach(document -> document.setTeamate(teamateToUpdate));
        }
        final Teamate teamateUpdated = repository.save(teamateToUpdate);
        return ResponseEntity.ok().body(teamateUpdated);
    }

    @DeleteMapping("/teamate/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
