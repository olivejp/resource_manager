package nc.deveo.resource_manager.controller.dto;

import lombok.Data;
import nc.deveo.resource_manager.domain.TypeConges;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CongesCreateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long teammateId;

    @NotNull
    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @NotNull
    private TypeConges typeConges;
}
