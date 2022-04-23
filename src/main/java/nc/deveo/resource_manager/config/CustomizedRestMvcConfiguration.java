package nc.deveo.resource_manager.config;

import nc.deveo.resource_manager.domain.Competence;
import nc.deveo.resource_manager.domain.Teamate;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class CustomizedRestMvcConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setDefaultMediaType(MediaType.APPLICATION_JSON)
                .setReturnBodyForPutAndPost(true)
                .exposeIdsFor(Teamate.class, Competence.class);
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
    }
}
