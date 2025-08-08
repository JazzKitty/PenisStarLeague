package com.psl.PenisStarLeague.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.psl.PenisStarLeague.dto.GameInfoDTO;
import com.psl.PenisStarLeague.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer>{
    List<Game> findAll(); 


    @Query(nativeQuery = true, value = """
            Select g.idGame, g.game, 
            COUNT(gl.idGameLeague) leagueNumbers, 
            COUNT(e.idEvent) eventNumbers 
            FROM Game g 
            LEFT JOIN [Event] e on e.idGame = g.idGame 
            LEFT JOIN GameLeague gl on gl.idGame = g.idGame 
            Group by g.idGame, g.game
            """)
    List<GameInfoDTO> getGameInfo();
}
