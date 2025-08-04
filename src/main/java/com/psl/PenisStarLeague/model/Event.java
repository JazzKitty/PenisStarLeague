package com.psl.PenisStarLeague.model;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psl.PenisStarLeague.model.dictionary.EventIntervalType;

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
    private Instant date; 

    @ManyToOne
    @JoinColumn(name = "idLeague", nullable = false)
    @JsonIgnore
    private League league; 

    @ManyToOne
    @JoinColumn(name = "idGame", nullable = false)
    private Game game; 

    @ManyToOne
    @JoinColumn(name = "idEventIntervalType", nullable = true)
    private EventIntervalType eventIntervalType; 

}
