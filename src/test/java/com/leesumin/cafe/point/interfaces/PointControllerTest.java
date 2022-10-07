package com.leesumin.cafe.point.interfaces;

import com.leesumin.cafe.IntegrationTest;
import com.leesumin.cafe.customer.application.CustomerService;

import com.leesumin.cafe.customer.interfaces.CustomerDto;
import com.leesumin.cafe.point.application.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PointControllerTest extends IntegrationTest {
    @Autowired CustomerService customerService;
    @Autowired PointService pointService;
    CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerService.createCustomer(CustomerDto.builder()
                .mobile("01010041004")
                .build());
        customerDto = customerService.findByMobile("01010041004");
        pointService.createPoint(customerDto.getId(), 0);
    }

    @Test
    void create() throws Exception {
        // given
        PointDto pointDto = PointDto.builder()
                .customerId(customerDto.getId())
                .amount(5000)
                .build();

        // when
        ResultActions ra = mvc.perform(post("/point")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(pointDto)))
                .andDo(print());

        // then
        ra.andExpect(status().isOk());
    }

    @Test
    void charge() throws Exception {
        // given
        PointDto pointDto = PointDto.builder()
                .customerId(customerDto.getId())
                .amount(20000)
                .build();

        // when
        ResultActions ra = mvc.perform(post("/point/charge")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(pointDto)))
                .andDo(print());

        // then
        ra.andExpect(status().isOk());
    }
}