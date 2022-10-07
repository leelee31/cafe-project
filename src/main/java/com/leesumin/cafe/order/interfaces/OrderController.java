package com.leesumin.cafe.order.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leesumin.cafe.order.application.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public void order(@RequestBody OrderDto orderDto) throws JsonProcessingException {
        orderService.order(orderDto);
    }
}