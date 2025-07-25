package com.psl.PenisStarLeague.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psl.PenisStarLeague.model.UserLeague;

public interface UserLeagueRepository extends JpaRepository<UserLeague, Integer> {


    
    @Query(nativeQuery = true, value = """
        Select Count(*) FROM UserLeague ul where ul.idUser = :idUser and ul.idLeague = :idLeague
        """)
    Optional<Integer> findByIdLeagueIdUser(@Param("idLeague") Integer idLeague, @Param("idUser") Integer idUser);
    
}
