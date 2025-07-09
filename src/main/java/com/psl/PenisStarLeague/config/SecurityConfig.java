package com.psl.PenisStarLeague.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.reactive.function.client.WebClient;

import com.psl.PenisStarLeague.controller.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final WebClient webClient; 

    public SecurityConfig(WebClient webClient){
        this.webClient = webClient; 
    }

    /**
     * Defines the security filter chain.
     *
     * @param http the HttpSecurity object to configure.
     * @return the SecurityFilterChain bean.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/", "/auth/**", "/public/**").permitAll()
                .anyRequest().authenticated()
        )
                .oauth2ResourceServer(c -> c.opaqueToken(Customizer.withDefaults()));
        ;
        return http.build();
    }

    @Bean
    public OpaqueTokenIntrospector introspector(){
        return new GoogleOpaqueTokenIntrospector(webClient); 
        
    }
}
