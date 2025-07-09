package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.PenisStarLeague.model.League;


import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {
    

    //Optional<League> findPublicByName(String leagueName);
}