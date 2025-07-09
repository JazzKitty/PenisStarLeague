package com.psl.PenisStarLeague.model.dictionary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="LeaguePosition")
/**
 * Specify type of league 
 */
public class LeaguePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLeaguePosition;
    private String leaguePosition;
    private String code; 
    
}
