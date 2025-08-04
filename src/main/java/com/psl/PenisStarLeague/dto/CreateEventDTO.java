package com.psl.PenisStarLeague.dto;

import java.time.Instant;

public record CreateEventDTO(Integer idLeague,
        Integer idGame,
        String event,
        String description,
        String isReaccuring,
        Instant date,
        Integer idEventIntervalType) {
}
