package nc.deveo.resource_manager.mapper;

import lombok.RequiredArgsConstructor;
import nc.deveo.resource_manager.controller.NotFoundException;
import nc.deveo.resource_manager.domain.Teammate;
import nc.deveo.resource_manager.repository.TeammateRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilsMapper {
    private final TeammateRepository teammateRepository;

    @Named("getTeammate")
    public Teammate getTeammate(Long idTeammate) {
        return teammateRepository.findById(idTeammate).orElseThrow(NotFoundException::new);
    }
}
