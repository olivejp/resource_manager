package nc.deveo.resource_manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import nc.deveo.resource_manager.domain.enums.NiveauCompetence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teammate_competence")
@Getter
@Setter
public class TeammateCompetence implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    TeammateCompetenceKey id;

    @ManyToOne
    @MapsId("competenceId")
    @JoinColumn(name = "competence_id", nullable = false)
    Competence competence;

    @ManyToOne
    @MapsId("teammateId")
    @JoinColumn(name = "teammate_id", nullable = false)
    Teammate teammate;

    @Column(name = "niveau_competence", nullable = false)
    @Enumerated(EnumType.STRING)
    NiveauCompetence niveau;
}
