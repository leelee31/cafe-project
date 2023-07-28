package com.leesumin.cafe.menu.application;

import com.leesumin.cafe.exception.MenuException;
import com.leesumin.cafe.menu.interfaces.MenuDto;
import com.leesumin.cafe.menu.domain.Menu;
import com.leesumin.cafe.menu.domain.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @InjectMocks MenuService menuService;
    @Mock MenuRepository menuRepository;
    @Test
    @DisplayName("메뉴 목록 조회")
    void findAllMenu_test() {
        // given
        List<Menu> mockMemus = new ArrayList<>();
        Menu menu1 = Menu.builder().name("아메리카노").price(2000).build();
        Menu menu2 = Menu.builder().name("라떼").price(3000).build();
        mockMemus.add(menu1);
        mockMemus.add(menu2);
        given(menuRepository.findAll()).willReturn(mockMemus);

        // when
        List<MenuDto> findMenus = menuService.findAllMenu();

        // then
        assertThat(findMenus.get(0).getName()).isEqualTo(mockMemus.get(0).getName());
    }

    @Test
    @DisplayName("메뉴명으로 조회")
    void findByname_test() {
        // given
        Menu mockMenu = Menu.builder().name("라떼").price(3000).build();
        given(menuRepository.findByName(any())).willReturn(Optional.of(mockMenu));

        // when
        MenuDto findMenu = menuService.findMenuByName("라떼");

        // then
        assertThat(findMenu.getName()).isEqualTo(mockMenu.getName());
    }

    @Test
    @DisplayName("메뉴명 중복 저장")
    void save_test() {
        // given
        Menu mockMenu = Menu.builder().name("라떼").price(3000).build();
        given(menuRepository.existsByName(mockMenu.getName())).willReturn(true);

        // when
        MenuDto dto = new MenuDto("라떼", 6000);

        // then
        assertThrows(MenuException.class, () -> menuService.createMenu(dto));
    }
}