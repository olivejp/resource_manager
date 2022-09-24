package nc.deveo.resource_manager.config.security;

import lombok.RequiredArgsConstructor;
import nc.deveo.resource_manager.config.JwtTokenVerifierFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {
    private static final String EXTERNAL_API_PREFIX = "/api/**";

    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;

    @Bean
    public Http401UnauthorizedEntryPoint securityException401EntryPoint() {
        return new Http401UnauthorizedEntryPoint();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(securityException401EntryPoint())
                .and()
                .headers()
                .frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenVerifierFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults())
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers("/jwt").permitAll()
                        .antMatchers("/alive").permitAll()
                        .antMatchers("/i18n/**").permitAll()
                        .antMatchers("/content/**").permitAll()
                        .antMatchers("/swagger-ui/index.html").permitAll()
                        .antMatchers("/test/**").permitAll()
                        .antMatchers("/b2b/jobs/**").permitAll()
                        .antMatchers("/management/health").permitAll()
                        .antMatchers("/management/info").permitAll()
                        .antMatchers("/management/configuration").permitAll()
                        .antMatchers("/v3/api-docs/**").permitAll()
                        .antMatchers("/swagger-resources/configuration/ui").permitAll()
                        .antMatchers("/swagger-ui/index.html").permitAll()
                        .antMatchers(EXTERNAL_API_PREFIX).authenticated()
                );
        return http.build();
    }
}
