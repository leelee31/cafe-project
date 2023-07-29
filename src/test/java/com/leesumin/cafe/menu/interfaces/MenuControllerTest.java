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
        menuService.createMenu(new MenuDto("아이스아메리카노", 2000));
        menuService.createMenu(new MenuDto("초코라떼", 5000));
    }

    @Test
    void saveMenu() throws Exception {
        // given
        MenuDto menuDto = new MenuDto("자몽아이스티", 6000);

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

    @Test
    void NOT_FOUND_MENU_EXCEPTION() throws Exception {
        // when
        ResultActions ra = mvc.perform(get("/menu/{name}", "바닐라라떼")).andDo(print());

        // then
        ra.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(501))
                .andExpect(jsonPath("errorMessage").value("메뉴 정보가 없습니다."));
    }
}