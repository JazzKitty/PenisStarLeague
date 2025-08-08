package com.psl.PenisStarLeague.repo;

import java.util.Optional;
import java.util.Set;

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
        where ul.idLeague = :idLeague and ul.idUser = (:idMembers)
        and ul.idLeague in (Select idLeague FROM UserLeague ul 
        LEFT JOIN LeaguePosition lp on lp.idLeaguePosition = ul.idLeaguePosition
        where lp.code = 'OWN' and ul.idUser = :idOwner)
            """)
    int removeMember(@Param("idLeague") int idLeague, @Param("idMembers") Set<Integer> idMembers, @Param("idOwner") int idOwner);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
        UPDATE ul 
        SET ul.idLeaguePosition = (Select idLeaguePosition FROM LeaguePosition where code = 'USR')
        FROM UserLeague ul
        where ul.idLeague = :idLeague and ul.idUser in (:idMembers)
        and ul.idLeague in (Select idLeague FROM UserLeague ul 
            LEFT JOIN LeaguePosition lp on lp.idLeaguePosition = ul.idLeaguePosition
            where lp.code = 'OWN' and ul.idUser = :idOwner)
            """)
    int addMember(@Param("idLeague") int idLeague, @Param("idMembers") Set<Integer> idMembers, @Param("idOwner") int idOwner);
    
}
