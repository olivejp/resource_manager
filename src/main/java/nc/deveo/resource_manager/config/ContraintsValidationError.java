package nc.deveo.resource_manager.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContraintsValidationError {

    private String type;
    private String error;
}
