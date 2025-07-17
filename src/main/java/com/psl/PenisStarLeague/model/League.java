package com.psl.PenisStarLeague.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*; // Or javax.persistence.* for older versions
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name="League")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLeague")
    private int idLeague; 
    private String league;
    private String description; 
    private int idLeagueType;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<UserLeague> userLeagues;

    public void addNewUserLeague(UserLeague userLeague){
        if(this.userLeagues == null){
            this.userLeagues = new ArrayList<>(); 
        }
        this.userLeagues.add(userLeague);
        userLeague.setLeague(this);
    }

    @OneToMany(mappedBy = "event")
    private Set<Event> events;

    public void addNewEvent(Event event){
        if(this.events == null){
            this.events = new HashSet<>(); 
        }
        this.events.add(event);
        event.setLeague(this);
    }
}
