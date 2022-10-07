package com.leesumin.cafe.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.leesumin.cafe.customer.interfaces.CustomerDto;
import com.leesumin.cafe.order.interfaces.OrderDto;
import com.leesumin.cafe.order.interfaces.OrderItemDto;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class OrderHistoryServiceTest {
    @InjectMocks OrderHistoryService orderHistoryService;

    CustomerDto customer;
    List<OrderItemDto> orderItems;

    @BeforeEach
    void setup() {
        customer = CustomerDto.builder()
                .mobile("01010001000")
                .build();

        orderItems = new ArrayList<>();
        OrderItemDto oi1 = OrderItemDto.builder().menuName("아메리카노").menuPrice(1000).counts(5).build();
        OrderItemDto oi2 = OrderItemDto.builder().menuName("자몽허니블랙티").menuPrice(5000).counts(10).build();
        orderItems.add(oi1);
        orderItems.add(oi2);
    }

    @Test
    @DisplayName("주문 내역 전송 정상 Mock Kafka")
    void orderHistoryService_send_test() throws JsonProcessingException {
        OrderDto mockOrder = OrderDto.builder()
                .customerId(customer.getId())
                .customerMobile(customer.getMobile())
                .orderItems(orderItems)
                .build();
        List<ProducerRecord<String, String>> historyResponse = orderHistoryService.send(mockOrder);

        assertAll(
                () -> assertTrue(historyResponse.size() == 1),
                () -> assertTrue(historyResponse.get(0).key().equalsIgnoreCase("OrderHistoryMessage"))
        );
    }

}