package com.leesumin.cafe.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leesumin.cafe.customer.application.CustomerRepository;
import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.exception.CustomerException;
import com.leesumin.cafe.exception.ErrorEnum;
import com.leesumin.cafe.exception.MenuException;
import com.leesumin.cafe.menu.domain.MenuRepository;
import com.leesumin.cafe.menu.interfaces.MenuDto;
import com.leesumin.cafe.order.domain.*;
import com.leesumin.cafe.order.interfaces.OrderDto;
import com.leesumin.cafe.menu.domain.Menu;

import com.leesumin.cafe.point.application.PointService;
import com.leesumin.cafe.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final PointService pointService;
    private final OrderHistoryService orderHistoryService;
    private final RedisTemplate<String, Object> redisTemplate;

    public Long order(OrderDto orderDto) throws JsonProcessingException {
        Customer customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new CustomerException(ErrorEnum.NOT_FOUND_CUSTOMER));

        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(oid -> {
                    Menu menu = menuRepository.findById(oid.getMenuId())
                            .orElseThrow(() -> new MenuException(ErrorEnum.NOT_FOUND_MENU));
                    return OrderItem.builder()
                            .menu(menu)
                            .counts(oid.getCounts())
                            .build();
                })
                .collect(Collectors.toList());

        Order order = Order.createOrder(customer, orderItems);

        orderRepository.save(order);

        pointService.usePoint(customer.getId(), order.getTotalPrice());

        OrderDto orderDtoToSend = OrderDto.OrderToDto(order);
        List<ProducerRecord<String, String>> orderHistory = orderHistoryService.send(orderDtoToSend);

        putRedisPopularMenu(orderItems);

        return order.getId();
    }

    private void putRedisPopularMenu(List<OrderItem> orderItems) {
        String key = RedisUtil.generateKeyYYYYMMWEEK(LocalDateTime.now());
        ObjectMapper ojm = new ObjectMapper();
        orderItems.forEach(oi -> {
                    Menu menu = oi.getMenu();
                    try {
                        redisTemplate.opsForZSet().incrementScore(key, ojm.writeValueAsString(MenuDto.builder()
                                .id(menu.getId())
                                .name(menu.getName())
                                .price(menu.getPrice())
                                .build()), 1);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}