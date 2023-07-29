package com.leesumin.cafe.order.interfaces;

import com.leesumin.cafe.IntegrationTest;
import com.leesumin.cafe.customer.application.CustomerService;

import com.leesumin.cafe.customer.interfaces.CustomerDto;
import com.leesumin.cafe.menu.application.MenuService;
import com.leesumin.cafe.menu.interfaces.MenuDto;
import com.leesumin.cafe.point.application.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends IntegrationTest {
    @Autowired CustomerService customerService;
    @Autowired PointService pointService;
    @Autowired MenuService menuService;
    List<OrderItemDto> orderItemDtoList;
    List<MenuDto> menuList;
    CustomerDto customer;

    @BeforeEach
    void setUp() {
        // 사용자, 포인트
        customerService.createCustomer(CustomerDto.builder()
                .mobile("01010041004")
                .build());
        customer = customerService.findByMobile("01010041004");
        pointService.createPoint(customer.getId(), 1000000);

         // 메뉴
        menuService.createMenu(new MenuDto("아이스 아메리카노", 2000));
        menuService.createMenu(new MenuDto("녹차라떼", 5000));
        menuService.createMenu(new MenuDto("레몬아이스티", 6000));
        menuList = menuService.findAllMenu();

        // 주문
        orderItemDtoList = new ArrayList<>();
        OrderItemDto orderItemDto1 = OrderItemDto.builder()
                .menuId(menuList.get(0).getId())
                .counts(10)
                .build();
        OrderItemDto orderItemDto2 = OrderItemDto.builder()
                .menuId(menuList.get(1).getId())
                .counts(10)
                .build();
        OrderItemDto orderItemDto3 = OrderItemDto.builder()
                .menuId(menuList.get(2).getId())
                .counts(10)
                .build();
        orderItemDtoList.add(orderItemDto1);
        orderItemDtoList.add(orderItemDto2);
        orderItemDtoList.add(orderItemDto3);
    }

    @Test
    void order() throws Exception {
        // given
        OrderDto orderDto = OrderDto.builder()
                .customerId(customer.getId())
                .orderItemDtos(orderItemDtoList)
                .build();

        // when
        ResultActions ra = mvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print());

        // then
        ra.andExpect(status().isOk());
    }

    @Test
    void NOT_FOUND_MENU_EXCEPTION() throws Exception {
        // given
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .menuId(1000L)
                .counts(1000)
                .build();
        orderItemDtoList.add(orderItemDto);
        OrderDto orderDto = OrderDto.builder()
                .customerId(customer.getId())
                .orderItemDtos(orderItemDtoList)
                .build();

        // when
        ResultActions ra = mvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print());

        // then
        ra.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(501))
                .andExpect(jsonPath("errorMessage").value("메뉴 정보가 없습니다."));
    }


    @Test
    void REJECT_USE_POINT_EXCEPTION() throws Exception {
        // given
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .menuId(menuList.get(2).getId())
                .counts(1000)
                .build();
        orderItemDtoList.add(orderItemDto);
        OrderDto orderDto = OrderDto.builder()
                .customerId(customer.getId())
                .orderItemDtos(orderItemDtoList)
                .build();

        //when
        ResultActions ra = mvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print());

        // then
        ra.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(401))
                .andExpect(jsonPath("errorMessage").value("포인트가 부족하여 결제에 실패했습니다."));
    }
}