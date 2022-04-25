package nc.deveo.resource_manager.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "competence")
@Getter
@Setter
public class Competence implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 1000)
    @Column(name = "nom", length = 1000)
    private String nom;

    @OneToMany(mappedBy = "competence")
    private List<TeammateCompetence> teammates;
}
