package com.psl.PenisStarLeague.controller;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psl.PenisStarLeague.dto.MessageDTO;
import com.psl.PenisStarLeague.dto.UserDTO;
import com.psl.PenisStarLeague.model.PSLUser;
import com.psl.PenisStarLeague.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
    
        OAuth2IntrospectionAuthenticatedPrincipal prince = (OAuth2IntrospectionAuthenticatedPrincipal) authentication.getPrincipal();
    
        PSLUser user = userService.getUser(prince.getAttribute("email"), prince.getAttribute("sub"), prince.getAttribute("name"));
        
        prince.getAttributes(); 
        UserDTO userDTO = new UserDTO(user.getIdUser(), user.getName(), user.getUserName()); 
        
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/createUserName")
    public ResponseEntity<MessageDTO> createUserName(Authentication authentication, @RequestParam String userName, @RequestParam String gamerTag) {

        if(userName == null || userName.length() < 3){
            return ResponseEntity.ok(new MessageDTO("User name is too short: ", "N"));
        }

        if(gamerTag == null || gamerTag.length() < 3){
            return ResponseEntity.ok(new MessageDTO("gamer tag is too short: ", "N"));
        }


        int idUser = userService.getIdUser(authentication); 

        boolean isAvailable = userService.createUserName(userName, gamerTag, idUser);
        if(isAvailable){
            return ResponseEntity.ok(new MessageDTO("", "Y"));
        }
        
        return ResponseEntity.ok(new MessageDTO("User name is taken ", "N"));
    }

}