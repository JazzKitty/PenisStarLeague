package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psl.PenisStarLeague.model.dictionary.LeaguePosition;

import java.util.Optional;


public interface LeaguePositionRepository extends JpaRepository<LeaguePosition, Integer> {
    Optional<LeaguePosition> findByCode(String code);

    @Query(nativeQuery = true, value = """
            Select lp.code FROM LeaguePosition lp 
            RIGHT JOIN UserLeague ul on ul.idLeaguePosition = lp.idLeaguePosition
            JOIN League l on l.idLeague = ul.idLeague
            WHERE l.idLeague = :idLeague and ul.idUser = :idUser 
            """)
    Optional<String> findByIdLeagueAndIdUser(@Param("idLeague") int idLeague, @Param("idUser") int idUser);
}
