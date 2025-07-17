package com.psl.PenisStarLeague.repo;

import com.psl.PenisStarLeague.model.dictionary.Month;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthRepository extends JpaRepository<Month, Integer> {
    List<Month> findAll(); 
}
