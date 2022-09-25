package nc.deveo.resource_manager.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import nc.deveo.resource_manager.domain.enums.CongesPortion;
import nc.deveo.resource_manager.domain.enums.TypeConges;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conges")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conges {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    @JsonSerialize
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamate_id", nullable = false)
    private Teammate teammate;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongesPortion portionDebut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CongesPortion portionFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeConges typeConges;

    @Column(columnDefinition = "longtext")
    private String commentaire;
}
