package nc.deveo.resource_manager.config;

import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nc.deveo.resource_manager.config.security.UserSecurity;
import nc.deveo.resource_manager.repository.TeammateRepository;
import nc.deveo.resource_manager.service.security.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final Integer AUTHORIZATION_BEARER_NUMBER_CHARACTER = 7;
    public static final String BEARER = "Bearer ";

    private final TeammateRepository repository;
    private final JwtService jwtService;
    private final List<String> noFilterList = List.of("/v3/api-docs", "/jwt");

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!noFilterList.contains(request.getRequestURI())) {
            final UserSecurity userSecurity = verifyToken(request);
            verifyTeammateExist(response, userSecurity);
        }
        filterChain.doFilter(request, response);
    }

    private void verifyTeammateExist(HttpServletResponse response, UserSecurity userSecurity) throws IOException {
        final String email = userSecurity.getEmail();
        if (!repository.existsByEmail(email)) {
            response.sendError(403, "L'utilisateur n'existe pas en base de données.");
        }
    }

    private UserSecurity verifyToken(HttpServletRequest request) {
        final String token = resolveToken(request);
        if (token != null && !token.isEmpty()) {
            final Object jwt = jwtService.decodeJwt(token);
            final UserSecurity userSecurity = getUserSecurityFromToken(jwt);
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userSecurity,
                    token, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return userSecurity;
        } else {
            log.warn("Aucun bearer a décodé.");
        }
        return null;
    }

    private UserSecurity getUserSecurityFromToken(Object firebaseToken) {
        final Map<String, String> obj = (Map<String, String>) ((Jws) firebaseToken).getBody();
        return UserSecurity.builder()
                .id(obj.get("jti"))
                .email(obj.get("sub"))
                .name(obj.get("nom"))
                .photoUrl(obj.get("photo"))
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
