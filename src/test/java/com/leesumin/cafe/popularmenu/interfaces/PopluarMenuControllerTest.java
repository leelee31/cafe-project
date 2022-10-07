package com.leesumin.cafe.popularmenu.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leesumin.cafe.IntegrationTest;
import com.leesumin.cafe.customer.application.CustomerService;
import com.leesumin.cafe.customer.interfaces.CustomerDto;
import com.leesumin.cafe.menu.application.MenuService;
import com.leesumin.cafe.menu.interfaces.MenuDto;
import com.leesumin.cafe.order.application.OrderService;
import com.leesumin.cafe.order.interfaces.OrderDto;
import com.leesumin.cafe.order.interfaces.OrderItemDto;
import com.leesumin.cafe.point.application.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PopularMenuControllerTest extends IntegrationTest {
    @Autowired OrderService orderService;
    @Autowired CustomerService customerService;
    @Autowired PointService pointService;
    @Autowired MenuService menuService;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // 사용자 생성
        customerService.createCustomer(CustomerDto.builder()
                .mobile("01010041004")
                .build());
        CustomerDto customerDto = customerService.findByMobile("01010041004");

        // 포인트 생성
        pointService.createPoint(customerDto.getId(), 1000000000);

        // 메뉴 생성
        menuService.createMenu(MenuDto.builder().name("아이스 아메리카노").price(2000).build());
        menuService.createMenu(MenuDto.builder().name("녹차라떼").price(5000).build());
        menuService.createMenu(MenuDto.builder().name("레몬아이스티").price(6000).build());
        menuService.createMenu(MenuDto.builder().name("바닐라라떼").price(4000).build());
        menuService.createMenu(MenuDto.builder().name("아인슈페너").price(5500).build());

        // 주문
        List<MenuDto> menuList = menuService.findAllMenu();

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        OrderItemDto orderItemDto1 = OrderItemDto.builder()
                .menuId(menuList.get(0).getId())
                .menuName(menuList.get(0).getName())
                .counts(10)
                .build();
        OrderItemDto orderItemDto2 = OrderItemDto.builder()
                .menuId(menuList.get(3).getId())
                .menuName(menuList.get(3).getName())
                .counts(1)
                .build();
        OrderItemDto orderItemDto3 = OrderItemDto.builder()
                .menuId(menuList.get(4).getId())
                .menuName(menuList.get(4).getName())
                .counts(1000)
                .build();
        OrderItemDto orderItemDto4 = OrderItemDto.builder()
                .menuId(menuList.get(3).getId())
                .menuName(menuList.get(3).getName())
                .counts(9998)
                .build();
        OrderItemDto orderItemDto5 = OrderItemDto.builder()
                .menuId(menuList.get(4).getId())
                .menuName(menuList.get(4).getName())
                .counts(7)
                .build();
        OrderItemDto orderItemDto6 = OrderItemDto.builder()
                .menuId(menuList.get(2).getId())
                .menuName(menuList.get(2).getName())
                .counts(9)
                .build();
        orderItemDtoList.add(orderItemDto1);
        orderItemDtoList.add(orderItemDto2);
        orderItemDtoList.add(orderItemDto3);
        orderItemDtoList.add(orderItemDto4);
        orderItemDtoList.add(orderItemDto5);
        orderItemDtoList.add(orderItemDto6);

        orderService.order(OrderDto.builder()
                .customerId(customerDto.getId())
                .customerMobile(customerDto.getMobile())
                .orderItems(orderItemDtoList)
                .build());
    }

    @Test
    void findPopularMenu() throws Exception {
        // given
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusDays(7);

        // when
        ResultActions ra = mvc.perform(get("/popular/menu")
                        .param("topN", String.valueOf(3))
                        .param("startDateTime", String.valueOf(startDateTime))
                        .param("endDateTime", String.valueOf(endDateTime)))
                .andDo(print());

        // then
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ranking").value(1))
                .andExpect(jsonPath("$[0].totalCount").value(9999))
                .andExpect(jsonPath("$[2].ranking").value(3))
                .andExpect(jsonPath("$[2].totalCount").value(10));
    }
}