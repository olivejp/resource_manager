package nc.deveo.resource_manager.mapper;

import nc.deveo.resource_manager.controller.dto.CongesCreateDto;
import nc.deveo.resource_manager.controller.dto.CongesDto;
import nc.deveo.resource_manager.domain.Conges;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {TeammateMapper.class, UtilsMapper.class})
public interface CongesMapper {

    @Mappings({
            @Mapping(target = "teammate", source = "teammateId", qualifiedByName = "getTeammate")
    })
    Conges toEntity(CongesCreateDto dto);

    CongesDto toDto(Conges dto);
}
