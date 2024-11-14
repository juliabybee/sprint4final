package com.example.demo.service.impl;

import com.example.demo.model.OrderDetail;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail findById(Integer id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(Integer id) {
        orderDetailRepository.deleteById(id);
    }
}