package com.psl.PenisStarLeague.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.PenisStarLeague.model.dictionary.EventIntervalType;
import com.psl.PenisStarLeague.model.dictionary.Month;
import com.psl.PenisStarLeague.model.dictionary.Week;
import com.psl.PenisStarLeague.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService; 

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

}
