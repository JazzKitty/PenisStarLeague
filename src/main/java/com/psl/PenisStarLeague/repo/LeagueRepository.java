package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psl.PenisStarLeague.dto.LeagueCardDTO;
import com.psl.PenisStarLeague.dto.LeagueDictDTO;
import com.psl.PenisStarLeague.model.League;

import lombok.val;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Integer> {

    @Query("""
        SELECT COUNT(l) FROM League l 
        left join l.userLeagues ul
        left join ul.leaguePosition lp
        WHERE l.league = :leagueName and lp.code = 'OWN'
        """)
    Optional<Integer> findByIdUserAndLeague( @Param("leagueName") String leagueName); 



    @Query(nativeQuery = true, value = """
                Select top 100 l.idLeague, l.league, pslu.userName as 'owner', l.idLeagueType, count(ul2.idUser) as userCount FROM League l left join UserLeague ul on ul.idLeague = l.idLeague 
                left join LeaguePosition lp on lp.idLeaguePosition = ul.idLeaguePosition
                left join PSLUser pslu on pslu.idUser = ul.idUser
                left join UserLeague ul2 on ul2.idLeague = l.idLeague
                WHERE lp.code = 'OWN'
                GROUP by l.idLeague, l.league, pslu.userName, l.idLeagueType
                ORDER By COUNT(ul2.idUser) 
            """)
    Optional<List<LeagueCardDTO>> getLeagueCards(); 


    @Query("""
        Select l FROM League l 
        join fetch l.userLeagues ul 
        join fetch ul.user u
        join fetch l.leagueType lt
        WHERE ((lt.code = 'PRIV' and u.idUser = :idUser) OR lt.code != 'PRIV') and l.idLeague = :idLeague
        """)
    Optional<League> findLeagueById(@Param("idLeague") Integer idLeague, @Param("idUser") Integer idUser);

        @Query("""
        SELECT l FROM League l 
        left join l.userLeagues ul
        left join ul.user u
        left join ul.leaguePosition lp
        WHERE l.idLeague = :idLeague and lp.code = 'OWN' and u.idUser = :idUser
        """)
    Optional<League> findByIdUserAndIdLeague( @Param("idLeague") Integer idLeague, @Param("idUser") Integer idUser); 

    @Query(nativeQuery = true, value = """
        SELECT l.idLeague as idLeague, l.league as league FROM League l 
        left join UserLeague ul on ul.idLeague = l.idLeague
        left join LeaguePosition lp on lp.idLeaguePosition = ul.idLeaguePosition
        WHERE lp.code = 'OWN' and ul.idUser = :idUser 
        """)
    List<LeagueDictDTO> findOwnedLeagues(@Param("idUser") Integer idUser);
    

    @Query("""
        Select l FROM League l 
        join fetch l.userLeagues ul 
        join fetch ul.user u
        join fetch l.leagueType lt 
        join fetch l.events e 
        WHERE l.idLeague = :idLeague
        """)
    Optional<League> findByIdLeague(@Param("idLeague") Integer idLeague);

    
}