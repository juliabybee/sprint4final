package com.example.demo.controller;

import com.example.demo.model.Game;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public String listGames(Model model) {
        List<Game> games = gameService.findAll();
        model.addAttribute("games", games);
        return "game/list";
    }

    @GetMapping("/view/{id}")
    public String viewGame(@PathVariable Integer id, Model model) {
        Game game = gameService.findById(id);
        if (game == null) {
            // Handle game not found
            return "redirect:/games";
        }
        model.addAttribute("game", game);
        return "game/view";
    }

    @GetMapping("/add")
    public String addGameForm(Model model) {
        model.addAttribute("game", new Game());
        return "game/add";
    }

    @PostMapping("/add")
    public String addGame(@ModelAttribute Game game) {
        gameService.save(game);
        return "redirect:/games";
    }

    @GetMapping("/edit/{id}")
    public String editGameForm(@PathVariable Integer id, Model model) {
        Game game = gameService.findById(id);
        if (game == null) {
            // Handle game not found
            return "redirect:/games";
        }
        model.addAttribute("game", game);
        return "game/edit";
    }

    @PostMapping("/edit/{id}")
    public String editGame(@PathVariable Integer id, @ModelAttribute Game game) {
        game.setGameId(id);
        gameService.save(game);
        return "redirect:/games/view/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteGameForm(@PathVariable Integer id, Model model) {
        Game game = gameService.findById(id);
        if (game == null) {
            // Handle game not found
            return "redirect:/games";
        }
        model.addAttribute("game", game);
        return "game/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteGame(@PathVariable Integer id) {
        gameService.deleteById(id);
        return "redirect:/games";
    }
}