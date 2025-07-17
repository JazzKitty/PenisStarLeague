package com.psl.PenisStarLeague.mapper;

import com.psl.PenisStarLeague.dto.LeagueTypeDTO;
import com.psl.PenisStarLeague.model.dictionary.LeagueType;

public class LeagueMapper {

    private LeagueMapper(){

    }

    public static LeagueTypeDTO toLeagueTypeDTO(LeagueType leagueType){
        return new LeagueTypeDTO(leagueType.getIdLeagueType(), leagueType.getName());
    }

}
