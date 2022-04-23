package nc.deveo.resource_manager.domain;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.Set;

@Projection(name = "withCompetence", types = {Teamate.class})
public interface TeamateProjection {

    Long getId();

    String getNom();

    String getPrenom();

    LocalDateTime getDateNaissance();

    Set<Competence> getListCompetence();

    Set<Contrat> getListContrat();

    String getPhotoUrl();

    String getDescription();

    String getTelephone();

    String getEmail();
}
