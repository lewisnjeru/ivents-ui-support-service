
package ivents.ivents_ui_support.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class HttpSecurityConfig {

    private final JWTFilterConfig jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable session management, APIs are stateless
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Disable CSRF for APIs
            .csrf(AbstractHttpConfigurer::disable)

            // CORS config (allow all origins here, adjust for production)
            .cors(AbstractHttpConfigurer::disable)

            // Endpoint authorization
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Secured endpoints
                .requestMatchers("/v1/clients/**").authenticated()
                // Everything else
                .anyRequest().permitAll()
            )

            // Add our custom JWT filter before Spring Security’s UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}