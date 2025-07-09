package com.psl.PenisStarLeague.model;

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
@Table(name="UserLeague")
/**
 * Link between users and their leagues 
 */
public class UserLeague {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUserLeague;
    private Integer idUser; 
    private Integer idLeague;
    private String name; // User name within the league. 
    private Integer idLeaguePosition; 
}
