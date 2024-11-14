package com.example.demo.controller;

import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private GameService gameService;

    @GetMapping
    public String listReviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "review/list";
    }

    @GetMapping("/view/{id}")
    public String viewReview(@PathVariable Integer id, Model model) {
        Review review = reviewService.findById(id);
        model.addAttribute("review", review);
        model.addAttribute("game", gameService.findById(review.getGame_id()));
        return "review/view";
    }

    @GetMapping("/add")
    public String addReviewForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("games", gameService.findAll());
        return "review/add";
    }

    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review) {
        reviewService.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/edit/{id}")
    public String editReviewForm(@PathVariable Integer id, Model model) {
        Review review = reviewService.findById(id);
        model.addAttribute("review", review);
        model.addAttribute("games", gameService.findAll());
        return "review/edit";
    }

    @PostMapping("/edit/{id}")
    public String editReview(@PathVariable Integer id, @ModelAttribute Review review) {
        review.setReview_id(id);
        reviewService.save(review);
        return "redirect:/reviews/view/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteReviewForm(@PathVariable Integer id, Model model) {
        Review review = reviewService.findById(id);
        model.addAttribute("review", review);
        return "review/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable Integer id) {
        reviewService.deleteById(id);
        return "redirect:/reviews";
    }
}