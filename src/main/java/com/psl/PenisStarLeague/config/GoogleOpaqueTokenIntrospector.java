package com.psl.PenisStarLeague.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import com.psl.PenisStarLeague.controller.RequiredArgsConstructor;
import com.psl.PenisStarLeague.dto.UserInfo;


@RequiredArgsConstructor
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final WebClient userInfoClient;

    public GoogleOpaqueTokenIntrospector(WebClient webClient){
        this.userInfoClient = webClient; 
    }
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        UserInfo userInfo = userInfoClient.get()
                .uri( uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("access_token", token)
                        .queryParam("scope","email", "openid")
                        .build())
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", userInfo.sub());
        attributes.put("name", userInfo.name());
        attributes.put("email", userInfo.email());
        return new OAuth2IntrospectionAuthenticatedPrincipal(userInfo.name(), attributes, null);
    }
}