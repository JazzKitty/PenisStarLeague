package com.psl.PenisStarLeague.model;

import jakarta.persistence.*; // Or javax.persistence.* for older versions
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="PSLUser")
/**
 * User object 
 */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;
    private String sub;
    private String name;
    private String email;   
    private String userName; 
}
