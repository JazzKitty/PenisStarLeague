package com.psl.PenisStarLeague.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import com.psl.PenisStarLeague.dto.TokenDTO;
import com.psl.PenisStarLeague.dto.UrlDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientId}")
    private String clientId; 
    @Value("${spring.security.oauth2.resourceserver.opaque-token.clientSecret}")
    private String clientSercet; 

    @GetMapping("/auth/url")
    public ResponseEntity<UrlDTO> auth(){
        String url = new GoogleAuthorizationCodeRequestUrl(clientId,
        "http://localhost:4200",
        Arrays.asList("email", "profile", "openid") ).build();
        return ResponseEntity.ok(new UrlDTO(url)); 
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<TokenDTO> callback(@RequestParam("code") String code){
        Set<String> scopes = new HashSet<>();
        scopes.add("email");
        scopes.add("openid");
        String token;
        try {
            token = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                new GsonFactory(),
                clientId,
                clientSercet,
                code,
                "http://localhost:4200"

            ).setScopes(scopes).execute().getAccessToken();
        }catch(IOException exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new TokenDTO(token));
    }
    
}
