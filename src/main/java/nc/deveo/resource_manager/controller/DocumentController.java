package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.domain.Document;
import nc.deveo.resource_manager.domain.Teammate;
import nc.deveo.resource_manager.repository.DocumentRepository;
import nc.deveo.resource_manager.repository.TeammateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DocumentController {

    private final DocumentRepository repository;
    private final TeammateRepository teammateRepository;

    @DeleteMapping("/document/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.debug("REST request to delete a document");
        try {
            final Document document = repository.findById(id).orElseThrow(TeammateNotFoundException::new);
            teammateRepository.findAllByListDocumentContaining(document)
                    .forEach(teammate -> teammate.getListDocument().remove(document));
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (TeammateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
