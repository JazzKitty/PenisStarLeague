package com.psl.PenisStarLeague.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.PenisStarLeague.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName); 
    Optional<User> findByIdUser(int idUser);
}