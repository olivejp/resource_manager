package nc.deveo.resource_manager.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "teammate_contrat_client")
@Getter
@Setter
public class TeammateContratClient implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    TeammateContratClientKey id;

    @ManyToOne
    @MapsId("teammateId")
    @JoinColumn(name = "teammate_id", nullable = false)
    Teammate teammate;

    @ManyToOne
    @MapsId("contratClientId")
    @JoinColumn(name = "contrat_client_id", nullable = false)
    ContratClient contratClient;

    @Column(name = "date_debut", nullable = false)
    LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    LocalDate dateFin;
}
