package com.example.demo.service;

import com.example.demo.model.Review;
import java.util.List;

public interface ReviewService {
    List<Review> findAll();
    Review findById(Integer id);
    void save(Review review);
    void deleteById(Integer id);
}