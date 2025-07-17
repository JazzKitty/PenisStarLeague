package com.psl.PenisStarLeague.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.PenisStarLeague.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer>{
    List<Game> findAll(); 
}
