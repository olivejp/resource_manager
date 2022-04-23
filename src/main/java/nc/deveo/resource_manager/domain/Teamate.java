package nc.deveo.resource_manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "teamate")
@Getter
@Setter
public class Teamate implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String EMAIL_PATTERN = "^([\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4})$|";
    public static final String TELEPHONE_PATTERN = "^(\\+687 ?)?([ .]?\\d\\d){3}$|";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    @JsonSerialize
    private Long id;

    @Size(max = 255)
    @Column(name = "nom", nullable = false)
    private String nom;

    @Size(max = 255)
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @PastOrPresent
    @Column(name = "date_naissance")
    private LocalDateTime dateNaissance;

    @Column(name = "photo_url", length = 2000)
    private String photoUrl;

    @Pattern(regexp = EMAIL_PATTERN)
    @Column(name = "email")
    private String email;

    @Pattern(regexp = TELEPHONE_PATTERN)
    @Column(name = "telephone")
    private String telephone;

    @Column(name = "description", length = 2000)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "teamate_competence",
            joinColumns = @JoinColumn(name = "teamate_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id"))
    private List<Competence> listCompetence;

    @JsonIgnoreProperties("teamate")
    @OneToMany(mappedBy = "teamate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contrat> listContrat = new ArrayList<>();

    @JsonIgnoreProperties("teamate")
    @OneToMany(mappedBy = "teamate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> listDocument = new ArrayList<>();
}
