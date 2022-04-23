package nc.deveo.resource_manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "document")
@Getter
@Setter
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    @JsonSerialize
    private Long id;

    @Column(name = "url", length = 2000)
    private String url;

    @Column(name = "filename", length = 2000)
    private String filename;

    @JsonIgnoreProperties("teamate")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamate_id", nullable = false)
    private Teamate teamate;
}
