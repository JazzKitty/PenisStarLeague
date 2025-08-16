package com.psl.PenisStarLeague.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.PenisStarLeague.dto.GameInfoDTO;
import com.psl.PenisStarLeague.model.Game;
import com.psl.PenisStarLeague.service.GameService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService; 

    @GetMapping("/public/getGames")
    public ResponseEntity<List<Game>> getGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/public/getGameInfo")
    public ResponseEntity<List<GameInfoDTO>> getGameInfo() {
        return ResponseEntity.ok(gameService.getGameInfo());
    }
}
