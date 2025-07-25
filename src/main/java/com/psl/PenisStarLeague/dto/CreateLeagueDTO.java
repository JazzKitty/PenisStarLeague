package com.psl.PenisStarLeague.dto;

import java.util.Set;

public record CreateLeagueDTO(String league, String description, Integer idLeagueType, Set<Integer> idGames) {
    
}
