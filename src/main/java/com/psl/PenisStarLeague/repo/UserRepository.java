package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.PenisStarLeague.model.PSLUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PSLUser, Integer> {

    Optional<PSLUser> findByEmail(String email);
    Optional<PSLUser> findByUserName(String userName); 
    Optional<PSLUser> findByIdUser(int idUser);
}