package com.leesumin.cafe.customer.interfaces;

import com.leesumin.cafe.IntegrationTest;

import com.leesumin.cafe.customer.application.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends IntegrationTest {
    @Autowired CustomerService customerService;

    @Test
    void findByMobile() throws Exception {
        //given
        customerService.createCustomer(CustomerDto.builder().mobile("01010041004").build());

        // when
        ResultActions ra = mvc.perform(get("/customer/{mobile}", "01010041004"))
                .andDo(print());

        // then
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.mobile").value("01010041004"));
    }

    @Test
    void createCustomer() throws Exception {
        // given
        CustomerDto customerDto = CustomerDto.builder()
                .mobile("01010041004")
                .build();

        // when
        ResultActions ra = mvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andDo(print());

        // then
        ra.andExpect(status().isOk());
    }

}