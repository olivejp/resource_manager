package nc.deveo.resource_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class ResourceManagerApplication implements RepositoryRestConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ResourceManagerApplication.class, args);
    }
}
