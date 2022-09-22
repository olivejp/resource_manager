package nc.deveo.resource_manager.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import nc.deveo.resource_manager.controller.validation.CreationTeammate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TeammateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String EMAIL_PATTERN = "^([\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4})$|";
    public static final String TELEPHONE_PATTERN = "^(\\+687 ?)?([ .]?\\d\\d){3}$|";

    @JsonSerialize
    private Long id;

    @Size(max = 255, groups = CreationTeammate.class)
    private String nom;

    @Size(max = 255, groups = CreationTeammate.class)
    private String prenom;

    @PastOrPresent(groups = CreationTeammate.class)
    private LocalDateTime dateNaissance;

    private String photo;

    @NotNull
    @Pattern(regexp = EMAIL_PATTERN, groups = CreationTeammate.class)
    private String email;

    @Pattern(regexp = TELEPHONE_PATTERN, groups = CreationTeammate.class)
    private String telephone;

    @Size(max = 2000, groups = CreationTeammate.class)
    private String description;

}
