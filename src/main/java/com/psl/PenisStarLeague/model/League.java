package com.psl.PenisStarLeague.model;

import jakarta.persistence.*; // Or javax.persistence.* for older versions
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="League")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLeague")
    private int idLeague; 
    private String league;
    private int idLeagueType;
}
