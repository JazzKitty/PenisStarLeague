package com.psl.PenisStarLeague.model;
import java.sql.Date;

import com.psl.PenisStarLeague.model.dictionary.EventIntervalType;
import com.psl.PenisStarLeague.model.dictionary.Month;
import com.psl.PenisStarLeague.model.dictionary.Week;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvent")
    private int idEvent;
    private String event; // name of event 
    private String description; 
    private String isReaccuring; 
    private Integer minute;
    private Integer hour;
    private Integer day;
    private Integer year; 
    private String amPm; 

    @ManyToOne
    @JoinColumn(name = "idLeague", nullable = false)
    private League league; 

    @ManyToOne
    @JoinColumn(name = "idGame", nullable = false)
    private Game game; 

    @ManyToOne
    @JoinColumn(name = "idEventIntervalType", nullable = true)
    private EventIntervalType eventIntervalType; 

    @ManyToOne
    @JoinColumn(name = "idMonth", nullable = true)
    private Month month;

    @ManyToOne
    @JoinColumn(name = "idWeek", nullable = true)
    private Week week;

}
