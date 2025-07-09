package com.psl.PenisStarLeague.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.model.League;
import com.psl.PenisStarLeague.repo.LeagueRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;

    public League createLeague(int idUser, String leagueName, int idLeagueType){
    
        return new League(); 

    }

    

}