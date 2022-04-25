package nc.deveo.resource_manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrat_travail")
@Getter
@Setter
public class ContratTravail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    @JsonSerialize
    private Long id;

    @Column(name = "type_contrat_travail", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeContratTravail typeContratTravail;

    @Column(name = "date_debut")
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @Column(name = "observation")
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamate_id", nullable = false)
    private Teammate teammate;

    @JsonIgnoreProperties("teammate")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> listDocument = new ArrayList<>();
}
