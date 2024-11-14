package com.example.demo.service;

import com.example.demo.model.OrderDetail;
import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findAll();
    OrderDetail findById(Integer id);
    void save(OrderDetail orderDetail);
    void deleteById(Integer id);
}