package nc.deveo.resource_manager.service;

import lombok.RequiredArgsConstructor;
import nc.deveo.resource_manager.controller.NotFoundException;
import nc.deveo.resource_manager.controller.dto.CongesCreateDto;
import nc.deveo.resource_manager.controller.dto.CongesDto;
import nc.deveo.resource_manager.controller.dto.TeammateDto;
import nc.deveo.resource_manager.domain.Conges;
import nc.deveo.resource_manager.domain.Teammate;
import nc.deveo.resource_manager.domain.TypeConges;
import nc.deveo.resource_manager.mapper.CongesMapper;
import nc.deveo.resource_manager.repository.CongesRepository;
import nc.deveo.resource_manager.repository.TeammateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
@Transactional
@RequiredArgsConstructor
public class CongesService {

    private final CongesRepository repository;
    private final CongesMapper mapper;

    public CongesDto save(CongesCreateDto dto) {
        switch (dto.getTypeConges()) {
            case AM:
                dto.setDateDebut(dto.getDateDebut().withHour(8).withMinute(0).withSecond(0));
                dto.setDateFin(dto.getDateDebut().withHour(12).withMinute(0).withSecond(0));
                break;
            case PM:
                dto.setDateDebut(dto.getDateDebut().withHour(13).withMinute(0).withSecond(0));
                dto.setDateFin(dto.getDateDebut().withHour(17).withMinute(0).withSecond(0));
                break;
            case ALL_DAY:
                dto.setDateDebut(dto.getDateDebut().withHour(8).withMinute(0).withSecond(0));
                dto.setDateFin(dto.getDateFin().withHour(17).withMinute(0).withSecond(0));
                break;
        }
        final Conges congesSaved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(congesSaved);
    }

    public Page<CongesDto> findAll(final Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public CongesDto findById(final Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}