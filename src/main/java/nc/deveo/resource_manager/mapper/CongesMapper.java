package nc.deveo.resource_manager.mapper;

import nc.deveo.resource_manager.controller.dto.CongesPersistDto;
import nc.deveo.resource_manager.controller.dto.CongesDto;
import nc.deveo.resource_manager.domain.Conges;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {TeammateMapper.class, UtilsMapper.class})
public interface CongesMapper {

    @Mappings({
            @Mapping(target = "teammate", source = "teammateId", qualifiedByName = "getTeammate"),
            @Mapping(target = "dateDebut", source = "dateDebut", dateFormat = "dd-MM-yyyy HH:mm:ss"),
            @Mapping(target = "dateFin", source = "dateFin", dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    Conges toEntity(CongesPersistDto dto);

    @Mappings({
            @Mapping(target = "dateDebut", source = "dateDebut", dateFormat = "dd-MM-yyyy HH:mm:ss"),
            @Mapping(target = "dateFin", source = "dateFin", dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    CongesDto toDto(Conges dto);
}
