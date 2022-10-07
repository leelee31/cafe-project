package com.leesumin.cafe.menu.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    @DisplayName("메뉴 생성")
    void createMenu_test() {
        Menu menu = Menu.builder()
                .name("아이스아메리카노")
                .price(2000)
                .build();

        assertAll(
                () -> assertNotNull(menu),
                () -> assertEquals("아이스아메리카노", menu.getName())
        );
    }
}