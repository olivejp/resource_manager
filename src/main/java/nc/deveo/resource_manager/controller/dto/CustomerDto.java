package nc.deveo.resource_manager.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize
    private Long id;

    @Size(max = 25)
    private String ridet;

    @Size(max = 255)
    private String raisonSociale;
}
