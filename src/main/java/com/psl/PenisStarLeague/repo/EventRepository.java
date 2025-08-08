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
