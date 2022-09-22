package nc.deveo.resource_manager.mapper;

import nc.deveo.resource_manager.controller.dto.TeammateDto;
import nc.deveo.resource_manager.domain.Teammate;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeammateMapper {
    TeammateDto toDto(Teammate entrainement);

    Teammate toEntity(TeammateDto dto);
}
