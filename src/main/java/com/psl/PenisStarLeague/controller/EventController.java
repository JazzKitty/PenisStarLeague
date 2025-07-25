package com.psl.PenisStarLeague.controller;

import java.net.Authenticator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.psl.PenisStarLeague.dto.CreateEventDTO;
import com.psl.PenisStarLeague.dto.EventCardDTO;
import com.psl.PenisStarLeague.model.Event;
import com.psl.PenisStarLeague.model.dictionary.EventIntervalType;
import com.psl.PenisStarLeague.model.dictionary.Month;
import com.psl.PenisStarLeague.model.dictionary.Week;
import com.psl.PenisStarLeague.service.EventService;
import com.psl.PenisStarLeague.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService; 
    private final UserService userService;

    @GetMapping("/public/getMonthes")
    public ResponseEntity<List<Month>> getMonthes() {
        return ResponseEntity.ok(eventService.getAllMonthes());
    }

    @GetMapping("/public/getWeeks")
    public ResponseEntity<List<Week>> getWeeks() {
        return ResponseEntity.ok(eventService.getAllWeeks());
    }

    @GetMapping("/public/getEventIntervalTypes")
    public ResponseEntity<List<EventIntervalType>> getEventIntervalTypes(){
        return ResponseEntity.ok(eventService.getAllEventIntervalTypes());
    }

    @PostMapping("/createEvent")
    public ResponseEntity<Void> createEvent(Authentication authentication, @RequestBody CreateEventDTO event){
        int idUser = userService.getIdUser(authentication);
        if(eventService.createEvent(event, idUser)){
            return ResponseEntity.accepted().build(); 
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getEventCards")
    public ResponseEntity<List<EventCardDTO>> getEventCards(Authentication authentication){
        int idUser = userService.getIdUser(authentication);
        return ResponseEntity.ok(eventService.getEventCards(idUser));
    }
    

}
