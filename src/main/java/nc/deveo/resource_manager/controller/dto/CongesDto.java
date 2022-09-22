package nc.deveo.resource_manager.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import nc.deveo.resource_manager.domain.TypeConges;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CongesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize
    private Long id;

    private TeammateDto teammate;

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    private TypeConges typeConges;
}
