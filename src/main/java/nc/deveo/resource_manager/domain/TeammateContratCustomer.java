package nc.deveo.resource_manager.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "teammate_contrat_customer")
@Getter
@Setter
public class TeammateContratCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    TeammateContratCustomerKey id;

    @ManyToOne
    @MapsId("teammateId")
    @JoinColumn(name = "teammate_id", nullable = false)
    Teammate teammate;

    @ManyToOne
    @MapsId("contratCustomerId")
    @JoinColumn(name = "contrat_customer_id", nullable = false)
    ContratCustomer contratCustomer;

    @Column(name = "date_debut", nullable = false)
    LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    LocalDate dateFin;
}
