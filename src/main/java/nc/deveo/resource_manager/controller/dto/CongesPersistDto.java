package nc.deveo.resource_manager.controller.dto;

import lombok.Data;
import nc.deveo.resource_manager.domain.enums.CongesPortion;
import nc.deveo.resource_manager.domain.enums.TypeConges;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CongesPersistDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Long teammateId;

    @NotNull
    private String dateDebut;

    @NotNull
    private String dateFin;

    @NotNull
    private CongesPortion portionDebut;

    @NotNull
    private CongesPortion portionFin;

    @NotNull
    private TypeConges typeConges;

    private String commentaire;
}
