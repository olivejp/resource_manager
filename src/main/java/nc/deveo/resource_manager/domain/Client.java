package nc.deveo.resource_manager.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    @JsonSerialize
    private Long id;

    @Column(name = "ridet")
    private String ridet;

    @Column(name = "raison_sociale", length = 2000)
    private String raisonSociale;
}
