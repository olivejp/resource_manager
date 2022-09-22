package nc.deveo.resource_manager.service;

import com.google.cloud.Tuple;
import lombok.RequiredArgsConstructor;
import nc.deveo.resource_manager.controller.NotFoundException;
import nc.deveo.resource_manager.domain.Document;
import nc.deveo.resource_manager.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository repository;
    private final FirebaseService firebaseService;

    public Tuple<String, byte[]> downloadFile(final Long id) throws IOException {
        final Document document = repository.findById(id).orElseThrow(NotFoundException::new);
        return Tuple.of(document.getFilename(), firebaseService.downloadFile(document.getFilename()));
    }

    public void delete(final Long id) {
        final Document document = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.delete(document);
    }
}
