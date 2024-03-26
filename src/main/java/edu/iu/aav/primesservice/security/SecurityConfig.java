package edu.iu.aav.primesservice.security;


import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAKey;
import java.security.security;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private RSAKey rsakey;
    public SecurityConfig() {
        this.rsakey = Jwks.generateRsa();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet jwkSet = new JWKSet (rsakey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }
    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(x -> x.disable())
                .authorizeHttpRequests (auth -> auth
                        .requestMatchers (
                                HttpMethod.POST, "/register", "/login").permitAll()
                .anyRequest().authenticated()
                )
                .sessionManagement (session ->
                    session.sessionCreationPolicy (SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer ((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}

