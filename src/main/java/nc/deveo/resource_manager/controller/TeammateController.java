package nc.deveo.resource_manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.controller.dto.TeammateDto;
import nc.deveo.resource_manager.controller.validation.CreationTeammate;
import nc.deveo.resource_manager.domain.Document;
import nc.deveo.resource_manager.repository.TeammateRepository;
import nc.deveo.resource_manager.service.TeammateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teammate")
public class TeammateController {

    private final TeammateRepository repository;
    private final TeammateService service;

    @GetMapping
    public ResponseEntity<Page<TeammateDto>> getAll(final Pageable pageable) {
        log.debug("REST request to get all teamate");
        return ResponseEntity.ok().body(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeammateDto> get(@PathVariable final Long id) {
        return service.findById(id)
                .map(teamate -> ResponseEntity.ok().body(teamate))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TeammateDto> create(@RequestBody @Validated(CreationTeammate.class) final TeammateDto dto) {
        final TeammateDto teammateCreated = service.save(dto);
        return ResponseEntity.ok().body(teammateCreated);
    }

    @PutMapping
    public ResponseEntity<TeammateDto> update(@RequestBody @Validated(CreationTeammate.class) final TeammateDto teammateToUpdate) {
        final TeammateDto teammateUpdated = service.update(teammateToUpdate);
        return ResponseEntity.ok().body(teammateUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable final Long id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<TeammateDto> patch(@PathVariable final Long id,
                                             @RequestBody final JsonPatch patch) {
        try {
            return ResponseEntity.ok(service.patch(id, patch));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(path = "/{id}/documents")
    public ResponseEntity<Set<Document>> getAllDocuments(@PathVariable final Long id) {
        return ResponseEntity.ok(service.getAllDocuments(id));
    }

    @PostMapping(path = "/{id}/documents", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Document> upload(@PathVariable final Long id,
                                           @RequestPart final MultipartFile multipartFile) throws IOException {
        final Document document = service.uploadDocument(id, multipartFile);
        return ResponseEntity.ok(document);
    }
}
