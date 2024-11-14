package com.example.demo.service.impl;

import com.example.demo.model.Game;
import com.example.demo.repository.GameRepository;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(Integer id) {
        return gameRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void deleteById(Integer id) {
        gameRepository.deleteById(id);
    }
}