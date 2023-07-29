package com.leesumin.cafe.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leesumin.cafe.customer.application.CustomerRepository;
import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.exception.CustomerException;
import com.leesumin.cafe.exception.ErrorEnum;
import com.leesumin.cafe.exception.MenuException;
import com.leesumin.cafe.menu.domain.MenuRepository;
import com.leesumin.cafe.order.domain.*;
import com.leesumin.cafe.order.interfaces.OrderDto;
import com.leesumin.cafe.menu.domain.Menu;

import com.leesumin.cafe.point.application.PointService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final PointService pointService;
    private final OrderHistoryService orderHistoryService;

    public Long order(OrderDto orderDto) throws JsonProcessingException {
        Customer customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new CustomerException(ErrorEnum.NOT_FOUND_CUSTOMER));

        List<Menu> menus = menuRepository.findAll();
        List<OrderItem> orderItems = orderDto.getOrderItemDtos().stream()
                .map(oid -> {
                    Menu menu = menus.stream()
                            .filter(m -> m.getId().equals(oid.getMenuId()))
                            .findAny()
                            .orElseThrow(() -> new MenuException(ErrorEnum.NOT_FOUND_MENU, oid.getMenuId()));
                    return oid.toEntity(menu);
                })
                .collect(toList());

        Order order = Order.createOrder(customer, orderItems);

        // 주문 저장
        orderRepository.save(order);

        // 결제
        pointService.usePoint(customer.getId(), order.getTotalPrice());

        // 주문 내역 발송
        OrderDto orderDtoToSend = new OrderDto(order);
        List<ProducerRecord<String, String>> orderHistory = orderHistoryService.send(orderDtoToSend);

        return order.getId();
    }
}