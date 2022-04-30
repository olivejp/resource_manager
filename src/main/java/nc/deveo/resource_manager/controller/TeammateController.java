package nc.deveo.resource_manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
    private final ObjectMapper objectMapper;

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


    @PatchMapping(path = "/teamate/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Teammate> patch(@PathVariable Long id,
                                          @RequestBody JsonPatch patch) {
        try {
            final Teammate teammateUpdated = repository.findById(id).orElseThrow(TeammateNotFoundException::new);
            final Teammate teammatePatched = applyPatchToTeammate(patch, teammateUpdated);
            repository.save(teammatePatched);
            return ResponseEntity.ok(teammatePatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (TeammateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private Teammate applyPatchToTeammate(JsonPatch patch, Teammate targetCustomer) throws JsonPatchException, JsonProcessingException {
        final JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Teammate.class);
    }
}
