package com.psl.PenisStarLeague.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.PenisStarLeague.model.dictionary.EventIntervalType;

public interface EventIntervalTypeRepository extends JpaRepository<EventIntervalType, Integer> {
    List<EventIntervalType> findAll();
} 
