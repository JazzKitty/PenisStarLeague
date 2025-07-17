package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.PenisStarLeague.model.dictionary.LeagueType;

import java.util.List;

public interface LeagueTypeRepository extends JpaRepository<LeagueType, Integer> {
    List<LeagueType> findAll(); 
}
