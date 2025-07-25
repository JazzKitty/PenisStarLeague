package com.psl.PenisStarLeague.model;

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
@Table(name="GameLeague")
public class GameLeague {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGameLeague")
    private Integer idGameLeague;
    
    @ManyToOne
    @JoinColumn(name = "idLeague", nullable = false)
    private League league; 

    @ManyToOne
    @JoinColumn(name = "idGame", nullable = false)
    private Game game;    
}
