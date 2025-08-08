package com.psl.PenisStarLeague.dto;

import java.time.Instant;

public record CalenderEventDTO(Integer idEvent, Integer idLeague, String name, Instant instant, String interval) {
    
}
