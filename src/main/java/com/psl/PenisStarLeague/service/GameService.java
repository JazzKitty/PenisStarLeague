package com.psl.PenisStarLeague.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.dto.GameInfoDTO;
import com.psl.PenisStarLeague.model.Game;
import com.psl.PenisStarLeague.repo.GameRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository; 

    public List<Game> getAllGames(){
        return gameRepository.findAll(); 
    }

    public List<GameInfoDTO> getGameInfo(){
        return gameRepository.getGameInfo();
    }
}
