
package com.psl.PenisStarLeague.service;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.checkerframework.checker.units.qual.min;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.dto.CalenderEventDTO;
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
import com.psl.PenisStarLeague.util.PSLUtil;

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

    public List<Month> getAllMonthes() {
        return monthRepository.findAll();
    }

    public List<Week> getAllWeeks() {
        return weekRepository.findAll();
    }

    public List<EventIntervalType> getAllEventIntervalTypes() {
        return eventIntervalTypeRepository.findAll();
    }

    public boolean createEvent(CreateEventDTO eventDTO, Integer idUser) {
        League league = leagueRepository.findByIdUserAndIdLeague(eventDTO.idLeague(), idUser).orElse(null);
        if (league == null) {
            return false;
        }
        Event event = new Event();
        event.setEvent(eventDTO.event());
        event.setDescription(eventDTO.description());
        event.setLeague(leagueRepository.getReferenceById(eventDTO.idLeague()));
        event.setGame(gameRepository.getReferenceById(eventDTO.idGame()));
        event.setIsReaccuring(eventDTO.isReaccuring());
        if (eventDTO.idEventIntervalType() != null) {
            event.setEventIntervalType(eventIntervalTypeRepository.getReferenceById(eventDTO.idEventIntervalType()));
        }
        event.setDate(eventDTO.date());
        eventRepository.save(event);

        return true;
    }

    public List<CalenderEventDTO> getCalenderEvents(int idUser, Instant startDate, Instant endDate, TimeZone timeZone) {
        // Grab time zone
        ZoneId id = PSLUtil.getIdZone(timeZone);

        // Create local min and max date
        LocalDate minDate = startDate.atZone(id).toLocalDate(); // first day in view
        LocalDate maxDate = endDate.atZone(id).toLocalDate(); // last day in view

        Instant maxInstant = maxDate.atStartOfDay(id).toInstant();
        List<Event> events = eventRepository.findEventsBeforeDate(maxInstant, idUser);

        List<CalenderEventDTO> calenderEventDTOs = new ArrayList<>();

        for (Event event : events) {
            if (event.getEventIntervalType() == null) {
                EventService.createSingleEvent(calenderEventDTOs, event, minDate, maxDate, id);
            } else {
                switch (event.getEventIntervalType().getIntervalType()) {
                    case "Yearly":
                        EventService.createYearlyEvents(calenderEventDTOs, event, minDate, maxDate, id);
                        break;
                    case "Monthly":
                        EventService.createMonthlyEvents(calenderEventDTOs, event, minDate, maxDate, id);
                        break;
                    case "Weekly":
                        EventService.createWeeklyEvents(calenderEventDTOs, event, minDate, maxDate, id);
                        break;
                    case "Daily":
                        EventService.createDailyEvents(calenderEventDTOs, event, minDate, maxDate, id);
                        break;
                }
            }
        }

        return calenderEventDTOs;
    }

    public List<EventCardDTO> getEventCards(Set<Integer> idEvents, int idUser, TimeZone timeZone) {
        List<EventCardDTO> eventCardDTOs = new ArrayList<>();
        ZoneId id = PSLUtil.getIdZone(timeZone);

        // Grab event and make sure user is part of league that contains that event
        List<Event> events = eventRepository.findEventsByIds(idEvents, idUser);
        String occursStr = "";

        for (Event event : events) {
            ZonedDateTime zonedDateTime = event.getDate().atZone(id);
            DecimalFormat df = new DecimalFormat("00");

            if (event.getEventIntervalType() == null) {
                occursStr = "Occurs on " + zonedDateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " "
                        + zonedDateTime.getDayOfMonth() + ", " + zonedDateTime.getYear() + " at "
                        + zonedDateTime.getHour() + ":" + zonedDateTime.getMinute();
            } else {
                switch (event.getEventIntervalType().getIntervalType()) {
                    case "Yearly":
                        occursStr = "Occurs yearly on "
                                + zonedDateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " "
                                + zonedDateTime.getDayOfMonth()+ PSLUtil.getPlaceString(zonedDateTime.getDayOfMonth()) + " at " + zonedDateTime.getHour() + ":"
                                + df.format(zonedDateTime.getMinute());
                        break;
                    case "Monthly":
                        occursStr = "Occurs Monthly on the " + zonedDateTime.getDayOfMonth() + PSLUtil.getPlaceString(zonedDateTime.getDayOfMonth())  +" at "
                                + zonedDateTime.getHour() + ":" + df.format(zonedDateTime.getMinute());
                        break;
                    case "Weekly":
                        occursStr = "Occurs Weekly every "
                                + zonedDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " at "
                                + zonedDateTime.getHour() + ":" + df.format(zonedDateTime.getMinute());
                        break;
                    case "Daily":
                        occursStr = "Occurs Daily at " + zonedDateTime.getHour() + ":" + df.format(zonedDateTime.getMinute());
                        break;
                }
            }
            EventCardDTO eventCardDTO = new EventCardDTO(event.getIdEvent(), event.getEvent(),
                    event.getLeague().getIdLeague(), event.getLeague().getLeague(), occursStr,
                    event.getGame().getGame(), event.getDescription());

            eventCardDTOs.add(eventCardDTO);

        }
        return eventCardDTOs;
    }

    /**
     * 
     * @param calenderEventDTOs
     * @param event
     * @param minDate
     * @param viewedDate
     * @param maxDate
     * @param id
     */
    private static void createYearlyEvents(List<CalenderEventDTO> calenderEventDTOs, Event event, LocalDate minDate, LocalDate maxDate, ZoneId id) {
        ZonedDateTime eventDate = event.getDate().atZone(id);
        LocalDate localEventDate = eventDate.toLocalDate();
 
        ZonedDateTime minEventDate = eventDate.withYear(minDate.getYear());
        ZonedDateTime maxEventDate = eventDate.withYear(maxDate.getYear());
        LocalDate minLocalEvent = maxEventDate.toLocalDate();
        LocalDate maxLocalDate = maxEventDate.toLocalDate();

        if((minLocalEvent.isAfter(minDate) || minLocalEvent.isEqual(minDate)) 
            && (minLocalEvent.isBefore(maxDate) || minLocalEvent.isEqual(maxDate))
            && (minLocalEvent.isAfter(localEventDate) || minLocalEvent.isEqual(localEventDate)) ){

            calenderEventDTOs.add( new CalenderEventDTO(event.getIdEvent(), event.getLeague().getIdLeague(),
                event.getEvent(), minEventDate.toInstant()));
        }
        if(maxLocalDate != minLocalEvent && 
            (maxLocalDate.isAfter(minDate) || maxLocalDate.isEqual(minDate)) 
                && (maxLocalDate.isBefore(maxDate) || maxLocalDate.isEqual(maxDate))
                && (maxLocalDate.isAfter(localEventDate) || maxLocalDate.isEqual(localEventDate))){
            calenderEventDTOs.add( new CalenderEventDTO(event.getIdEvent(), event.getLeague().getIdLeague(),
                event.getEvent(), maxEventDate.toInstant()));
        }

    }

    /**
     * 
     * @param calenderEventDTOs
     * @param event
     * @param minDate
     * @param viewedDate
     * @param maxDate
     * @param id
     */
    private static void createMonthlyEvents(List<CalenderEventDTO> calenderEventDTOs, Event event, LocalDate minDate,
            LocalDate maxDate, ZoneId id) {
        ZonedDateTime eventDate = event.getDate().atZone(id);
        LocalDate localEventDate = eventDate.toLocalDate();

        ZonedDateTime monthlyDateTime = eventDate.withMonth(minDate.getMonthValue()).withYear(minDate.getYear());
        LocalDate localMonthlyDate = monthlyDateTime.toLocalDate();

        while (localMonthlyDate.isBefore(maxDate) || localMonthlyDate.isEqual(maxDate)) {
            if ((localMonthlyDate.isAfter(localEventDate) || localMonthlyDate.isEqual(localEventDate))
                    && (localMonthlyDate.isAfter(minDate) || localMonthlyDate.isEqual(minDate))) {
                calenderEventDTOs.add(new CalenderEventDTO(event.getIdEvent(), event.getLeague().getIdLeague(),
                        event.getEvent(), monthlyDateTime.toInstant()));
            }
           
            monthlyDateTime =  monthlyDateTime.plusMonths(1);
            localMonthlyDate = monthlyDateTime.toLocalDate();
        }
    }

    /**
     * 
     * @param calenderEventDTOs
     * @param event
     * @param minDate
     * @param viewedDate
     * @param maxDate
     * @param id
     */
    private static void createWeeklyEvents(List<CalenderEventDTO> calenderEventDTOs, Event event, LocalDate minDate, LocalDate maxDate, ZoneId id) {
        ZonedDateTime eventDate = event.getDate().atZone(id);
        LocalDate localEventDate = eventDate.toLocalDate();

        DayOfWeek dayOfWeek = eventDate.getDayOfWeek();
        int dayDiff;
        if (dayOfWeek.compareTo(minDate.getDayOfWeek()) < 0) {
            dayDiff = (eventDate.getDayOfWeek().getValue() + 7) - minDate.getDayOfWeek().getValue();
        } else {
            dayDiff = (eventDate.getDayOfWeek().getValue()) - minDate.getDayOfWeek().getValue();
        }
        ZonedDateTime weekDateTime = eventDate.withMonth(minDate.getMonthValue())
                .withDayOfMonth(minDate.getDayOfMonth() + dayDiff);

        while (!weekDateTime.toLocalDate().isAfter(localEventDate)) {
            if (localEventDate.isBefore(weekDateTime.toLocalDate())
                    || localEventDate.isEqual(weekDateTime.toLocalDate())) {
                calenderEventDTOs.add(new CalenderEventDTO(event.getIdEvent(), event.getLeague().getIdLeague(),
                        event.getEvent(), weekDateTime.toInstant()));
            }
            weekDateTime = weekDateTime.plusDays(7); // add a week
            localEventDate = eventDate.toLocalDate();

        }
    }

    /**
     * 
     * @param calenderEventDTOs
     * @param event
     * @param minDate
     * @param viewedDate
     * @param maxDate
     * @param id
     */
    private static void createDailyEvents(List<CalenderEventDTO> calenderEventDTOs, Event event, LocalDate minDate, LocalDate maxDate, ZoneId id) {
        ZonedDateTime eventDate = event.getDate().atZone(id);
        LocalDate localEventDate = eventDate.toLocalDate();

        ZonedDateTime dailyDateTime = eventDate.withMonth(minDate.getMonthValue()).withDayOfMonth(1);
        while (!dailyDateTime.toLocalDate().isAfter(maxDate)) {
            if (localEventDate.isBefore(dailyDateTime.toLocalDate())
                    || localEventDate.isEqual(dailyDateTime.toLocalDate())) {
                calenderEventDTOs.add(new CalenderEventDTO(event.getIdEvent(), event.getLeague().getIdLeague(),
                        event.getEvent(), dailyDateTime.toInstant()));
            }
            dailyDateTime = dailyDateTime.plusDays(1); // add a week
            localEventDate = eventDate.toLocalDate();
        }
    }

    private static void createSingleEvent(List<CalenderEventDTO> calenderEventDTOs, Event event, LocalDate minDate,
            LocalDate maxDate, ZoneId id) {
        ZonedDateTime eventDate = event.getDate().atZone(id);
        LocalDate localEventDate = eventDate.toLocalDate();
        if (!localEventDate.isBefore(minDate) && !localEventDate.isAfter(maxDate)) {
            calenderEventDTOs.add(new CalenderEventDTO(event.getIdEvent(), event.getLeague().getIdLeague(),
                    event.getEvent(), eventDate.toInstant()));
        }
    }

}
