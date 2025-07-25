package com.psl.PenisStarLeague.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psl.PenisStarLeague.dto.CreateLeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueCardDTO;
import com.psl.PenisStarLeague.dto.LeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueDictDTO;
import com.psl.PenisStarLeague.model.dictionary.LeagueType;
import com.psl.PenisStarLeague.service.LeagueService;
import com.psl.PenisStarLeague.service.UserService;
import com.psl.PenisStarLeague.util.PSLUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;
    private final UserService userService; 

    @GetMapping("/public/getLeagueCards")
    public ResponseEntity<List<LeagueCardDTO>> getLeagueCards() {
        return ResponseEntity.ok(leagueService.getLeagueCards());
    }

    @GetMapping("/public/getLeagueTypes")
    public ResponseEntity<List<LeagueType>> getLeagueTypes() {
        List<LeagueType> leagueTypes = leagueService.getLeagueTypes(); 
        
        return ResponseEntity.ok(leagueTypes);
    }

    @GetMapping("/public/getLeague")
    public ResponseEntity<LeagueDTO> getLeague(Authentication authentication, @RequestParam Integer idLeague) {
        LeagueDTO league; 
        if(authentication!=null){
            try{
                int idUser = userService.getIdUser(authentication); 
                league = leagueService.getLeague(idLeague, idUser);
            }catch(UsernameNotFoundException e){
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build(); // no permisions 
            }
        }else{
            league = leagueService.getLeague(idLeague);
        }

        if(league == null){
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build(); // no permisions 
        }else{
            return ResponseEntity.ok(league);
        }
    }

    @PostMapping("/createLeague")
    public ResponseEntity<Void> createLeague(Authentication authentication,  @RequestBody CreateLeagueDTO createLeagueDTO) {
        int idUser = userService.getIdUser(authentication); 
        boolean created = leagueService.createLeague(idUser, createLeagueDTO);
        if(created){
            return ResponseEntity.status(HttpStatus.SC_CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/deleteLeague")
    public ResponseEntity<Void> deleteLeague(Authentication authentication, @RequestParam Integer idLeague){
        int idUser = userService.getIdUser(authentication); 

        boolean deleted = leagueService.deleteLeague(idLeague, idUser);
        if(deleted){
            return ResponseEntity.status(HttpStatus.SC_ACCEPTED).build();
        }else{
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        }
    }

    @PostMapping("/editTitle")
    public ResponseEntity<Void> editTitle(Authentication authentication,  @RequestParam Integer idLeague, @RequestParam String title) {
        int idUser = userService.getIdUser(authentication); 
        boolean created = leagueService.editTitle(idLeague, idUser, title);
        if(created){
            return ResponseEntity.status(HttpStatus.SC_CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        }
    }

    @PostMapping("/editDescription")
    public ResponseEntity<Void> editDescription(Authentication authentication, @RequestParam Integer idLeague, @RequestParam String description) {
        int idUser = userService.getIdUser(authentication); 
        boolean created = leagueService.editDescription(idLeague, idUser, description);
        if(created){
            return ResponseEntity.status(HttpStatus.SC_CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        }
    }

    @PostMapping("/editPrimaryGames")
    public ResponseEntity<Void> editPrimaryGames(Authentication authentication, @RequestParam Integer idLeague, @RequestParam String primaryGames) {
        int idUser = userService.getIdUser(authentication); 
        Set<Integer> idGames = PSLUtil.stringToSet(primaryGames, ","); 

        boolean created = leagueService.editPrimaryGames(idLeague, idUser, idGames);
        if(created){
            return ResponseEntity.status(HttpStatus.SC_CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        }
    }

    @GetMapping("/getOwnedLeagues")
    public ResponseEntity<List<LeagueDictDTO>> getOwnedLeagues(Authentication authentication) {
        int idUser = userService.getIdUser(authentication); 
        return ResponseEntity.ok(leagueService.getOwnedLeagues(idUser));
    }

    @PostMapping("/requestToJoin")
    public ResponseEntity<Void> editDescription(Authentication authentication,  @RequestParam Integer idLeague) {
        int idUser = userService.getIdUser(authentication); 
        boolean requested = leagueService.requestToJoin(idUser, idLeague); 
        if(requested){
            return ResponseEntity.status(HttpStatus.SC_CREATED).build();
        }else{
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
        }
    }

}