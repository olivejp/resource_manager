package nc.deveo.resource_manager.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import nc.deveo.resource_manager.config.security.UserSecurity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirebaseTokenVerifier {

    public UserSecurity verifyToken(String token) {
        log.debug("verifyToken called with {}", token);
        if (token != null && !token.isEmpty()) {
            token = token.substring(1, token.length() - 1);
            try {
                final FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
                return getUserSecurityFromToken(firebaseToken);
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
                log.error("Firebase Exception: {}", e.getLocalizedMessage());
            }
        } else {
            log.warn("Aucun bearer a décodé.");
        }
        return null;
    }

    private UserSecurity getUserSecurityFromToken(FirebaseToken firebaseToken) {
        return UserSecurity.builder()
                .emailVerified(firebaseToken.isEmailVerified())
                .name(firebaseToken.getName())
                .photoUrl(firebaseToken.getPicture())
                .name(firebaseToken.getName())
                .id(firebaseToken.getUid())
                .email(firebaseToken.getEmail())
                .build();
    }
}
