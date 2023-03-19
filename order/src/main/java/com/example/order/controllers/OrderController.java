package com.example.order.controllers;

import com.example.order.domain.Order;
import com.example.order.domain.OrderItem;
import com.example.order.repositories.OrderItemRepository;
import com.example.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @PostMapping(value = "/order")
    public ResponseEntity<Order> createNewOrder(@RequestBody Order orderData)
    {
        Order order = orderRepository.save(orderData);
        if (order == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
    @GetMapping("/order/search")
    public List<Order> searchOrder(){
        List<Order> order = orderRepository.findAll();
        if (order.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");
        return order;
    }

    @GetMapping(value = "/orderItems")
    public List<OrderItem> list() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        return orderItemList;
    }





}
