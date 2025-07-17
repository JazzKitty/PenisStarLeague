package com.psl.PenisStarLeague.repo;
import com.psl.PenisStarLeague.model.dictionary.Week;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Week, Integer>{
    List<Week> findAll(); 
}
