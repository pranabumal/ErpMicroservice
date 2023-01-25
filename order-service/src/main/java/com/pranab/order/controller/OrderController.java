package com.pranab.order.controller;

import com.pranab.order.dto.OrderRequest;
import com.pranab.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;
    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Place successfully";
    }
}
