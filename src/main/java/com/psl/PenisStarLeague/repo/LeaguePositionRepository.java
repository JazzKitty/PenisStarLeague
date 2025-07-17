package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.psl.PenisStarLeague.model.dictionary.LeaguePosition;

import java.util.Optional;


public interface LeaguePositionRepository extends JpaRepository<LeaguePosition, Integer> {
    Optional<LeaguePosition> findByCode(String code);
}
