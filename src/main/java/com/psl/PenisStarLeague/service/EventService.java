
package com.psl.PenisStarLeague.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.dto.CreateEventDTO;
import com.psl.PenisStarLeague.dto.EventCardDTO;
import com.psl.PenisStarLeague.model.Event;
import com.psl.PenisStarLeague.model.League;
import com.psl.PenisStarLeague.model.dictionary.EventIntervalType;
import com.psl.PenisStarLeague.model.dictionary.Month;
import com.psl.PenisStarLeague.model.dictionary.Week;
import com.psl.PenisStarLeague.repo.EventIntervalTypeRepository;
import com.psl.PenisStarLeague.repo.EventRepository;
import com.psl.PenisStarLeague.repo.GameRepository;
import com.psl.PenisStarLeague.repo.LeagueRepository;
import com.psl.PenisStarLeague.repo.MonthRepository;
import com.psl.PenisStarLeague.repo.WeekRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final MonthRepository monthRepository; 
    private final WeekRepository weekRepository; 
    private final EventRepository eventRepository; 
    private final EventIntervalTypeRepository eventIntervalTypeRepository;
    private final LeagueRepository leagueRepository;
    private final GameRepository gameRepository;

    public List<Month> getAllMonthes(){
        return monthRepository.findAll(); 
    }

    public List<Week> getAllWeeks(){
        return weekRepository.findAll(); 
    }

    public List<EventIntervalType> getAllEventIntervalTypes(){
        return eventIntervalTypeRepository.findAll(); 
    }

    public boolean createEvent(CreateEventDTO eventDTO, Integer idUser){    
        League league = leagueRepository.findByIdUserAndIdLeague(eventDTO.idLeague(),  idUser).orElse(null);
        if(league == null){
            return false; 
        }
        Event event = new Event();
        event.setEvent(eventDTO.event());
        event.setDescription(eventDTO.description());
        event.setLeague(leagueRepository.getReferenceById(eventDTO.idLeague()));
        event.setGame(gameRepository.getReferenceById(eventDTO.idGame()));
        event.setIsReaccuring(eventDTO.isReaccuring());
        if(eventDTO.idEventIntervalType() != null){
            event.setEventIntervalType(eventIntervalTypeRepository.getReferenceById(eventDTO.idEventIntervalType()));
        }
        event.setDay(eventDTO.day());
        if(eventDTO.idWeek() != null){
            event.setWeek(weekRepository.getReferenceById(eventDTO.idWeek()));
        }
        if(eventDTO.idMonth() != null){
            event.setMonth(monthRepository.getReferenceById(eventDTO.idMonth()));
        }
        event.setYear(eventDTO.year());
        event.setMinute(eventDTO.minute());
        event.setHour(eventDTO.hour());
        event.setAmPm(eventDTO.amPm());
        eventRepository.save(event);

        return true;  
    
    }

    public List<EventCardDTO> getEventCards(int idUser){
        return eventRepository.findUserEventCards(idUser).orElse(new ArrayList<>());
    }

}
