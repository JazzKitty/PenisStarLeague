
package com.psl.PenisStarLeague.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.model.dictionary.EventIntervalType;
import com.psl.PenisStarLeague.model.dictionary.Month;
import com.psl.PenisStarLeague.model.dictionary.Week;
import com.psl.PenisStarLeague.repo.EventIntervalTypeRepository;
import com.psl.PenisStarLeague.repo.MonthRepository;
import com.psl.PenisStarLeague.repo.WeekRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final MonthRepository monthRepository; 
    private final WeekRepository weekRepository; 
    private final EventIntervalTypeRepository eventIntervalTypeRepository;

    public List<Month> getAllMonthes(){
        return monthRepository.findAll(); 
    }

    public List<Week> getAllWeeks(){
        return weekRepository.findAll(); 
    }

    public List<EventIntervalType> getAllEventIntervalTypes(){
        return eventIntervalTypeRepository.findAll(); 
    }

}
