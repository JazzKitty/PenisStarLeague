package com.psl.PenisStarLeague.model;

import java.util.Date;

import com.psl.PenisStarLeague.model.dictionary.LeaguePosition;

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
@Table(name="UserLeague")
public class UserLeague {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUserLeague;
    private Date joinDate; 

    @ManyToOne
    @JoinColumn(name = "idLeague", nullable = false)
    private League league; 

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private PSLUser user; 

    @ManyToOne
    @JoinColumn(name = "idLeaguePosition", nullable = false)
    private LeaguePosition leaguePosition;
}
