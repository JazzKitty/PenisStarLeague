package com.psl.PenisStarLeague.repo;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psl.PenisStarLeague.dto.EventCardDTO;
import com.psl.PenisStarLeague.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

    // @Query(nativeQuery = true, value = """
    //         Select e.idEvent as idEvent, l.league as league, g.game as game, e.event as event,
    //         e.description as description, e.isReaccuring as isReaccuring, 
    //         eit.intervalType as eventInterval, e.date as date FROM Event e
    //         Left Join League l on l.idLeague = e.idLeague
    //         Left Join UserLeague ul on ul.idLeague = l.idLeague
    //         Left Join LeaguePosition lp on ul.idLeaguePosition = lp.idLeaguePosition
    //         Left Join Game g on g.idGame = e.idGame
    //         Left Join EventIntervalType eit on eit.idEventIntervalType = e.idEventIntervalType
    //         WHERE ul.idUser = :idUser and ul.idLeaguePosition is not null and lp.code <> 'PND'
    //         """)
    // Optional<List<EventCardDTO>> findUserEventCards(@Param("idUser") Integer idUser, @Param("minDate") LocalDate minDate, @Param("maxDate"), );

    @Query("""
            Select e FROM Event e 
            LEFT JOIN e.league l
            LEFT JOIN l.userLeagues ul 
            LEFT JOIN ul.user u 
            where e.date < :maxDate and u.idUser = :idUser  
            """)
    List<Event> findEventsBeforeDate(@Param("maxDate") Instant maxDate, @Param("idUser") int idUser);

    @Query("""
            Select e FROM Event e 
            LEFT JOIN e.league l
            LEFT JOIN l.userLeagues ul 
            LEFT JOIN ul.user u 
            where e.idEvent in (:idEvents) and u.idUser = :idUser  
            """)
    List<Event> findEventsByIds(@Param("idEvents") Set<Integer> idEvents, @Param("idUser") int idUser);
}
