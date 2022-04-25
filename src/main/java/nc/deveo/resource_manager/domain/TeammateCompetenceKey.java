package nc.deveo.resource_manager.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class TeammateCompetenceKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "competence_id")
    Long competenceId;

    @Column(name = "teammate_id")
    Long teammateId;
}
