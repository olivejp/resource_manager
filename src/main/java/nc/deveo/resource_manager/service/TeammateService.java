package nc.deveo.resource_manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import nc.deveo.resource_manager.controller.NotFoundException;
import nc.deveo.resource_manager.controller.dto.TeammateDto;
import nc.deveo.resource_manager.domain.Document;
import nc.deveo.resource_manager.domain.Teammate;
import nc.deveo.resource_manager.mapper.TeammateMapper;
import nc.deveo.resource_manager.repository.TeammateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class TeammateService {

    private final TeammateRepository repository;
    private final TeammateMapper mapper;
    private final ObjectMapper objectMapper;
    private final FirebaseService firebaseService;

    public Page<TeammateDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public Optional<TeammateDto> findById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public TeammateDto save(TeammateDto teammateToSave) {
        final Teammate teammateUpdated = repository.save(mapper.toEntity(teammateToSave));
        return mapper.toDto(teammateUpdated);
    }

    public TeammateDto update(final TeammateDto dto) {
        repository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        return save(dto);
    }

    public TeammateDto patch(final Long id, final JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        final Teammate teammateToUpdate = repository.findById(id).orElseThrow(NotFoundException::new);
        final Teammate teammatePatched = applyPatchToTeammate(patch, teammateToUpdate);
        final Teammate teammateSaved = repository.save(teammatePatched);
        return mapper.toDto(teammateSaved);
    }

    private Teammate applyPatchToTeammate(JsonPatch patch, Teammate teamate) throws JsonPatchException, JsonProcessingException {
        final JsonNode patched = patch.apply(objectMapper.convertValue(teamate, JsonNode.class));
        return objectMapper.treeToValue(patched, Teammate.class);
    }

    public Document uploadDocument(final Long idTeammate, final MultipartFile multipartFile) throws IOException {
        final Teammate teammate = repository.findById(idTeammate).orElseThrow(NotFoundException::new);

        final String fileName = firebaseService.generateFileName(multipartFile);
        final Blob blob = firebaseService.uploadFile(fileName, multipartFile);
        final String downloadUrl = blob.getName();

        final Document document = new Document();
        document.setFilename(fileName);
        document.setUrl(downloadUrl);
        teammate.getListDocument().add(document);
        repository.save(teammate);

        return document;
    }

    public Set<Document> getAllDocuments(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new).getListDocument();
    }
}
