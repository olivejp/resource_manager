package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.domain.Client;
import nc.deveo.resource_manager.repository.ClientRepository;
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
public class ClientController {

    private final ClientRepository repository;

    @GetMapping("/client")
    public ResponseEntity<Page<Client>> getAll(final Pageable pageable) {
        log.debug("REST request to get all teamate");
        final Page<Client> teamatePage = repository.findAll(pageable);
        return ResponseEntity.ok().body(teamatePage);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> get(@PathVariable final Long id) {
        return repository.findById(id)
                .map(teamate -> ResponseEntity.ok().body(teamate))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/client")
    public ResponseEntity<Client> create(@RequestBody @Valid Client teammateToCreate) {
        final Client teammateCreated = repository.save(teammateToCreate);
        return ResponseEntity.ok().body(teammateCreated);
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id,
                                         @RequestBody @Valid Client teammateToUpdate) {
        final Client teammateUpdated = repository.save(teammateToUpdate);
        return ResponseEntity.ok().body(teammateUpdated);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
