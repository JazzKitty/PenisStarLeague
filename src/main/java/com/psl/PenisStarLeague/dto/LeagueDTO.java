package com.psl.PenisStarLeague.dto;

import java.util.Set;

import com.psl.PenisStarLeague.model.Game;

public record LeagueDTO(int idLeague,
        String league,
        String owner,
        String isPrivate,
        int memberCount,
        String description,
        String leaguePositionCode, 
        Set<UserLeagueDTO> users,
        Set<PendingUserLeagueDTO> pendingUsers,
        Set<Game> games,
        Set<LeagueEventDTO> events) {

}
