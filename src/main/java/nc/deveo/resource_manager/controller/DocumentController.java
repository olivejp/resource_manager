package nc.deveo.resource_manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nc.deveo.resource_manager.domain.Document;
import nc.deveo.resource_manager.repository.DocumentRepository;
import nc.deveo.resource_manager.repository.TeammateRepository;
import nc.deveo.resource_manager.service.FirebaseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DocumentController {

    private final DocumentRepository repository;
    private final TeammateRepository teammateRepository;

    private final FirebaseService firebaseService;

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

    @GetMapping("/document/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) throws Exception {
        byte[] bytes = firebaseService.downloadFile(filename);
        return ResponseEntity
                .ok()
                .contentLength(bytes.length)
                .header("Content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(bytes);
    }

    @PostMapping(path = "/document", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> upload(@RequestParam String name, @RequestPart MultipartFile document) throws IOException {
        final String downloadUrl = firebaseService.uploadFile(document);
        return ResponseEntity.ok()
                .contentLength(downloadUrl.length())
                .header("Content-type", MediaType.TEXT_PLAIN_VALUE)
                .body(downloadUrl);
    }
}
