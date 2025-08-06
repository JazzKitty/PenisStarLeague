package com.psl.PenisStarLeague.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psl.PenisStarLeague.model.UserLeague;

import jakarta.transaction.Transactional;

public interface UserLeagueRepository extends JpaRepository<UserLeague, Integer> {

    @Query(nativeQuery = true, value = """
        Select Count(*) FROM UserLeague ul where ul.idUser = :idUser and ul.idLeague = :idLeague
        """)
    Optional<Integer> findByIdLeagueIdUser(@Param("idLeague") Integer idLeague, @Param("idUser") Integer idUser);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
        DELETE ul FROM UserLeague ul 
        where ul.idLeague = :idLeague and ul.idUser = :idMember
        and ul.idLeague in (Select idLeague FROM UserLeague ul 
            LEFT JOIN LeaguePosition lp on lp.idLeaguePosition = ul.idLeaguePosition
            where lp.code = 'OWN'  and ul.idUser = :idOwner)
            """)
    int removeMember(@Param("idLeague") int idLeague, @Param("idMember") int idMember, @Param("idOwner") int idOwner);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
        UPDATE UserLeague ul 
        SET ul.idLeaguePosition = (Select idLeaguePosition FROM LeaguePosition where code = 'USR')
        where ul.idLeague = :idLeague and ul.idUser = :idMember
        and ul.idLeague in (Select idLeague FROM UserLeague ul 
            LEFT JOIN LeaguePosition lp on lp.idLeaguePosition = ul.idLeaguePosition
            where lp.code = 'OWN' and ul.idUser = :idOwner)
            """)
    int addMember(@Param("idLeague") int idLeague, @Param("idMember") int idMember, @Param("idOwner") int idOwner);
    
}
