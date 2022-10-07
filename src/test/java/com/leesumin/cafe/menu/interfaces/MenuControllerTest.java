package com.leesumin.cafe.menu.interfaces;

import com.leesumin.cafe.IntegrationTest;

import com.leesumin.cafe.menu.application.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest extends IntegrationTest {
    @Autowired MenuService menuService;

    @BeforeEach
    void setUp() {
        menuService.createMenu(MenuDto.builder().name("아이스아메리카노").price(2000).build());
        menuService.createMenu(MenuDto.builder().name("초코라떼").price(5000).build());
    }

    @Test
    void saveMenu() throws Exception {
        // given
        MenuDto menuDto = MenuDto.builder().name("자몽아이스티").price(6000).build();

        //when
        ResultActions ra = mvc.perform(post("/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuDto)));

        //then
        ra.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findAllMenu() throws Exception {
        // when
        ResultActions ra = mvc.perform(get("/menu")).andDo(print());

        // then
        ra.andExpect(status().isOk());
    }

    @Test
    void findMenuByName() throws Exception {
        // when
        ResultActions ra = mvc.perform(get("/menu/{name}", "초코라떼")).andDo(print());

        // then
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("초코라떼"))
                .andExpect(jsonPath("$.price").value(5000));
    }
}