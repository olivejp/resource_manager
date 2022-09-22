package nc.deveo.resource_manager.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nc.deveo.resource_manager.config.security.SecurityConfiguration;
import nc.deveo.resource_manager.config.security.UserSecurity;
import nc.deveo.resource_manager.repository.TeammateRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseTokenVerifierFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final Integer AUTHORIZATION_BEARER_NUMBER_CHARACTER = 7;
    public static final String BEARER = "Bearer ";

    private final TeammateRepository repository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        verifyToken(request);
        verifyTeammateExist(response);
        filterChain.doFilter(request, response);
    }

    private void verifyTeammateExist(HttpServletResponse response) throws IOException {
        final String email = ((UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        if (!repository.existsByEmail(email)) {
            response.sendError(403, "L'utilisateur n'existe pas en base de données.");
        }
    }

    private void verifyToken(HttpServletRequest request) {
        final String token = resolveToken(request);
        if (token != null && !token.isEmpty()) {
            try {
                final FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
                final UserSecurity userSecurity = getUserSecurity(firebaseToken);
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userSecurity,
                        token, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
                log.error("Firebase Exception: {}", e.getLocalizedMessage());
            }
        } else {
            log.warn("Aucun bearer a décodé.");
        }
    }

    private UserSecurity getUserSecurity(FirebaseToken firebaseToken) {
        return UserSecurity.builder()
                .emailVerified(firebaseToken.isEmailVerified())
                .name(firebaseToken.getName())
                .photoUrl(firebaseToken.getPicture())
                .name(firebaseToken.getName())
                .uid(firebaseToken.getUid())
                .build();
    }

    private String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(AUTHORIZATION_BEARER_NUMBER_CHARACTER);
        }
        return null;
    }
}
