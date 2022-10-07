package com.leesumin.cafe.order.domain;

import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.menu.domain.Menu;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @Test
    void createOrder_test() {
        Customer customer = Customer.builder()
                .id(Long.valueOf(12345))
                .build();
        List<OrderItem> orderItems = new ArrayList<>();
        Menu menu1 = Menu.builder().name("아메리카노").price(1000).build();
        Menu menu2 = Menu.builder().name("라떼").price(2000).build();
        OrderItem oi1 = OrderItem.builder().menu(menu1).counts(10).build();
        OrderItem oi2 = OrderItem.builder().menu(menu2).counts(1).build();
        orderItems.add(oi1);
        orderItems.add(oi2);

        Order order = Order.createOrder(customer, orderItems);

        assertAll(
                () -> assertNotNull(order),
                () -> assertEquals(customer.getId(), order.getCustomer().getId())
        );
    }
}