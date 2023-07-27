package com.leesumin.cafe.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.leesumin.cafe.customer.application.CustomerRepository;
import com.leesumin.cafe.customer.domain.Customer;
import com.leesumin.cafe.customer.interfaces.CustomerDto;
import com.leesumin.cafe.menu.domain.MenuRepository;
import com.leesumin.cafe.order.interfaces.OrderDto;
import com.leesumin.cafe.order.interfaces.OrderItemDto;
import com.leesumin.cafe.menu.domain.Menu;
import com.leesumin.cafe.order.domain.Order;
import com.leesumin.cafe.order.domain.OrderItem;
import com.leesumin.cafe.order.domain.OrderRepository;
import com.leesumin.cafe.point.application.PointService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock CustomerRepository customerRepository;
    @Mock OrderRepository orderRepository;
    @Mock MenuRepository menuRepository;
    @Mock PointService pointService;
    @Mock OrderHistoryService orderHistoryService;

    @InjectMocks OrderService orderService;

    Customer customer;
    CustomerDto customerDto;
    List<OrderItemDto> orderItemDtos;
    List<OrderItem> orderItems;
    OrderDto orderDto;
    Integer price1 = 1000, price2 = 5000;
    Integer counts1 = 5, counts2 = 10;

    @BeforeEach
    void setup() {
        customer = Customer.builder()
                .mobile("01010001000")
                .build();

        customerDto = CustomerDto.builder()
                .mobile("01010001000")
                .build();

        orderItemDtos = new ArrayList<>();
        OrderItemDto oid1 = OrderItemDto.builder().menuName("아메리카노").menuPrice(price1).counts(counts1).build();
        OrderItemDto oid2 = OrderItemDto.builder().menuName("자몽허니블랙티").menuPrice(price2).counts(counts2).build();
        orderItemDtos.add(oid1);
        orderItemDtos.add(oid2);
        orderDto = OrderDto.builder().customerMobile(customerDto.getMobile()).orderItems(orderItemDtos).build();

        Menu menu1 = Menu.builder().name("아메리카노").price(price1).build();
        Menu menu2 = Menu.builder().name("자몽허니블랙티").price(price2).build();
        orderItems = new ArrayList<>();
        OrderItem oi1 = OrderItem.builder().menu(menu1).counts(counts1).build();
        OrderItem oi2 = OrderItem.builder().menu(menu2).counts(counts2).build();
        orderItems.add(oi1);
        orderItems.add(oi2);
    }


    @Test
    @DisplayName("주문 정상")
    void orderSuccess_test() throws JsonProcessingException {
        // given
        given(customerRepository.findById(any())).willReturn(Optional.ofNullable(customer));
        given(pointService.usePoint(any(), any())).willReturn(1L);

        Menu mockMenu1 = Menu.builder().name("아메리카노").price(price1).build();
        given(menuRepository.findById(any())).willReturn(Optional.of(mockMenu1));

        Order mockOrder = Order.createOrder(customer, orderItems);
        given(orderRepository.findById(any())).willReturn(Optional.of(mockOrder));

        given(orderHistoryService.send(any())).willReturn(null);

        // when
        Long orderId = orderService.order(orderDto);
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertThat(findOrder.getTotalPrice()).isEqualTo(price1 * counts1 + price2 * counts2);
    }
}