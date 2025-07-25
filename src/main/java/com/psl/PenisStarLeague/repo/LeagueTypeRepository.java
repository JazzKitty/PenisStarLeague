package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psl.PenisStarLeague.model.dictionary.LeagueType;

import java.util.List;
import java.util.Optional;

public interface LeagueTypeRepository extends JpaRepository<LeagueType, Integer> {
    List<LeagueType> findAll(); 
    

    @Query(nativeQuery = true, value = """
            Select lt.code FROM LeagueType lt 
            LEFT JOIN League l on l.idLeagueType = lt.idLeagueType
            WHERE l.idLeague = :idLeague
            """)
    Optional<String> findLeagueTypeCodeByIdLeague(@Param("idLeague") Integer idLeague);
}
