package com.example.demo.controller;

import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderdetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private GameService gameService;

    @GetMapping
    public String listOrderDetails(Model model) {
        List<OrderDetail> orderDetails = orderDetailService.findAll();
        model.addAttribute("orderDetails", orderDetails);
        return "orderdetail/list";
    }

    @GetMapping("/view/{id}")
    public String viewOrderDetail(@PathVariable Integer id, Model model) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("game", gameService.findById(orderDetail.getGame_id()));
        return "orderdetail/view";
    }

    @GetMapping("/add")
    public String addOrderDetailForm(Model model) {
        model.addAttribute("orderDetail", new OrderDetail());
        model.addAttribute("games", gameService.findAll());
        return "orderdetail/add";
    }

    @PostMapping("/add")
    public String addOrderDetail(@ModelAttribute OrderDetail orderDetail) {
        orderDetailService.save(orderDetail);
        return "redirect:/orderdetails";
    }

    @GetMapping("/edit/{id}")
    public String editOrderDetailForm(@PathVariable Integer id, Model model) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("games", gameService.findAll());
        return "orderdetail/edit";
    }

    @PostMapping("/edit/{id}")
    public String editOrderDetail(@PathVariable Integer id, @ModelAttribute OrderDetail orderDetail) {
        orderDetail.setOrder_id(id);
        orderDetailService.save(orderDetail);
        return "redirect:/orderdetails/view/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderDetailForm(@PathVariable Integer id, Model model) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        model.addAttribute("orderDetail", orderDetail);
        return "orderdetail/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable Integer id) {
        orderDetailService.deleteById(id);
        return "redirect:/orderdetails";
    }
}