package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Integer id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("games", gameService.findById(id));
        return "order/view";
    }

    @GetMapping("/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order/add";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        User user = userService.getUserById(order.getUser_id()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        order.setUser(user);
        orderService.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Integer id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        model.addAttribute("games", gameService.findAll());
        return "order/edit";
    }

    @PostMapping("/edit/{id}")
    public String editOrder(@PathVariable Integer id, @ModelAttribute Order order) {
        order.setOrder_id(id);
        orderService.save(order);
        return "redirect:/orders/view/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderForm(@PathVariable Integer id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteById(id);
        return "redirect:/orders";
    }
}