package com.psl.PenisStarLeague.model.dictionary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Week")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWeek") 
    private Integer idWeek;
    private String weekDay; 
}
