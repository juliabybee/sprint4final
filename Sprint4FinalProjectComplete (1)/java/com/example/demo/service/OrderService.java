package com.example.demo.service;

import com.example.demo.model.Order;
import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Integer id);
    void save(Order order);
    void deleteById(Integer id);
}