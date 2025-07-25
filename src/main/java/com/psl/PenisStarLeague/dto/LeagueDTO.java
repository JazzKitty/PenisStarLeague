package com.psl.PenisStarLeague.dto;

import java.util.Set;

import com.psl.PenisStarLeague.model.Game;

public record LeagueDTO(int idLeague,
        String league,
        String owner,
        String isOwner,
        String isMember,
        String isPrivate,
        String isPending,
        int memberCount,
        String description,
        Set<UserLeagueDTO> users,
        Set<Game> games) {

}
