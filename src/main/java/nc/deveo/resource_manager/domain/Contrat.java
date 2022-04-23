package nc.deveo.resource_manager.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "contrat")
@Getter
@Setter
public class Contrat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    @JsonSerialize
    private Long id;

    @Column(name = "type_contrat", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;

    @Column(name = "date_debut")
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @Column(name = "observation")
    private String observation;

    @Column(name = "contrat_url", length = 3000)
    private String contratUrl;

    @Column(name = "contrat_filename", length = 2000)
    private String contratFilename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamate_id", nullable = false)
    private Teamate teamate;
}
