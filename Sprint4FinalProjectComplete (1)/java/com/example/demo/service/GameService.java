package com.example.demo.service;

import com.example.demo.model.Game;
import java.util.List;

public interface GameService {
    List<Game> findAll();
    Game findById(Integer id);
    void save(Game game);
    void deleteById(Integer id);
}



