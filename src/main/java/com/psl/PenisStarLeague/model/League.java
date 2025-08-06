package com.psl.PenisStarLeague.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psl.PenisStarLeague.model.dictionary.LeagueType;

import jakarta.persistence.*; // Or javax.persistence.* for older versions
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

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<UserLeague> userLeagues;

    public void addNewUserLeague(UserLeague userLeague){
        if(this.userLeagues == null){
            this.userLeagues = new ArrayList<>(); 
        }
        this.userLeagues.add(userLeague);
        userLeague.setLeague(this);
    }

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private Set<Event> events;

    public void addNewEvent(Event event){
        if(this.events == null){
            this.events = new HashSet<>(); 
        }
        this.events.add(event);
        event.setLeague(this);
    }

    public void addNewGameLeague(GameLeague gameLeague){
        if(this.gameLeagues == null){
            this.gameLeagues = new HashSet<>(); 
        }
        this.gameLeagues.add(gameLeague);
        gameLeague.setLeague(this);
    }

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GameLeague> gameLeagues;

    @ManyToOne
    @JoinColumn(name = "idLeagueType", nullable = false)
    private LeagueType leagueType;
}
