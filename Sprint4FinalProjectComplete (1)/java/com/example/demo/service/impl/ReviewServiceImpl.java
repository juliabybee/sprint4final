package com.example.demo.service.impl;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }
}