package com.psl.PenisStarLeague.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psl.PenisStarLeague.dto.EventCardDTO;
import com.psl.PenisStarLeague.dto.LeagueDictDTO;
import com.psl.PenisStarLeague.model.Event;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(nativeQuery = true, value = """
            Select e.idEvent as idEvent, l.league as league, g.game as game, e.event as event,
            e.description as description, e.isReaccuring as isReaccuring, e.minute as minute, e.hour as hour,
            e.year as year, e.day as day, m.month as month, w.weekDay as weekDay, eit.intervalType as eventInterval, e.amPm as amPm FROM Event e
            Left Join League l on l.idLeague = e.idLeague
            Left Join UserLeague ul on ul.idLeague = l.idLeague
            Left Join LeaguePosition lp on ul.idLeaguePosition = lp.idLeaguePosition
            Left Join Game g on g.idGame = e.idGame
            Left Join EventIntervalType eit on eit.idEventIntervalType = e.idEventIntervalType
            Left Join [Week] w on w.idWeek = e.idWeek
            Left Join [Month] m on m.idMonth = e.idMonth
            WHERE ul.idUser = :idUser and ul.idLeaguePosition is not null and lp.code <> 'PND'
            """)
    Optional<List<EventCardDTO>> findUserEventCards(@Param("idUser") Integer idUser);

}
