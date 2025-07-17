package com.psl.PenisStarLeague.dto;

import java.util.Set;

public record LeagueDTO(int idLeague,
        String league,
        String owner,
        String isOwner,
        int memberCount,
        String description,
        Set<UserLeagueDTO> users,
        Set<EventLeagueDTO> events) {

}
