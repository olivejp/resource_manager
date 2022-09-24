package nc.deveo.resource_manager.service.security;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import nc.deveo.resource_manager.config.FirebaseTokenVerifier;
import nc.deveo.resource_manager.config.security.UserSecurity;
import nc.deveo.resource_manager.controller.NotFoundException;
import nc.deveo.resource_manager.domain.Teammate;
import nc.deveo.resource_manager.repository.TeammateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class JwtService {

    public static final String ISSUER = "DEVEO-Teammanager";
    public static final String ALGORITHM = "HmacSHA256";
    private final TeammateRepository teammateRepository;
    private final FirebaseTokenVerifier firebaseTokenVerifier;
    private final SecretKey secretKey;

    public JwtService(final TeammateRepository teammateRepository,
                      @Value("${resource.jwt-key}") final String keyValue,
                      final FirebaseTokenVerifier firebaseTokenVerifier) {
        this.teammateRepository = teammateRepository;
        this.firebaseTokenVerifier = firebaseTokenVerifier;
        byte[] decodedKey = Base64.getDecoder().decode(keyValue);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    /**
     * Génération d'un JWT depuis le Token Firebase.
     *
     * @param firebaseToken String
     * @return Token JWT.
     */
    public String generateJwt(final String firebaseToken) {
        log.debug("generateJwt called with {}", firebaseToken);
        final UserSecurity userSecurity = firebaseTokenVerifier.verifyToken(firebaseToken);
        final Teammate teammate = teammateRepository.findByEmail(userSecurity.getEmail()).orElseThrow(NotFoundException::new);
        return Jwts.builder()
                .setClaims(Map.of(
                        "nom", Optional.ofNullable(teammate.getNom()).orElse(""),
                        "prenom", Optional.ofNullable(teammate.getPrenom()).orElse(""),
                        "telephone", Optional.ofNullable(teammate.getTelephone()).orElse("")
                ))
                .setId(String.valueOf(teammate.getId()))
                .setIssuer(ISSUER)
                .setSubject(teammate.getEmail())
                .signWith(secretKey)
                .compact();
    }

    public Object decodeJwt(String jwtToDecode) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .requireIssuer(ISSUER)
                .build()
                .parse(jwtToDecode);
    }
}
