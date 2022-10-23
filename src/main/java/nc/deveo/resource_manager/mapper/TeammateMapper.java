package nc.deveo.resource_manager.mapper;

import nc.deveo.resource_manager.controller.dto.TeammateDto;
import nc.deveo.resource_manager.domain.Teammate;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeammateMapper {

    @Mappings({
            @Mapping(target = "dateNaissance", source = "dateNaissance", dateFormat = "dd/MM/yyyy"),
    })
    TeammateDto toDto(Teammate entrainement);

    @Mappings({
            @Mapping(target = "dateNaissance", source = "dateNaissance", dateFormat = "dd/MM/yyyy"),
    })
    Teammate toEntity(TeammateDto dto);
}
