package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.PenisStarLeague.model.GameLeague;

public interface GameLeagueRepository extends JpaRepository<GameLeague, Integer>{
    
}
