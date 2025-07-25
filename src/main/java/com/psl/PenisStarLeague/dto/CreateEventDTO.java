package com.psl.PenisStarLeague.dto;

public record CreateEventDTO(Integer idLeague,
        Integer idGame,
        String event,
        String description,
        String isReaccuring,
        Integer minute,
        Integer hour, 
        Integer day, 
        Integer year, 
        Integer idMonth, 
        Integer idWeek,
        Integer idEventIntervalType, 
        String amPm) {
}
