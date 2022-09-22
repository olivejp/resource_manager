package nc.deveo.resource_manager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@Setter
@ConfigurationProperties(prefix = "resource")
public class ResourceProperties {
    private String firebaseAdminKey;
    private final CorsConfiguration cors = new CorsConfiguration();
}
