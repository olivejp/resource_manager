package nc.deveo.resource_manager.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import nc.deveo.resource_manager.ResourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    @Bean
    public FirebaseApp firebaseApp(final ResourceProperties properties) throws IOException {
        log.info("Lancement de l'application Firebase. \u2764");
        final String adminKey = properties.getFirebaseAdminKey();
        log.info(adminKey);

        final GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(adminKey.getBytes()));

        final FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
