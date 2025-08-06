package com.psl.PenisStarLeague.model;

import jakarta.persistence.*; // Or javax.persistence.* for older versions
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name="PSLUser")
/**
 * User object 
 */
public class PSLUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;
    private String sub;
    private String name;
    private String email;   
    private String userName; 
    private String gamerTag;
    private String bio; 
}
