package com.leesumin.cafe.menu.application;

import com.leesumin.cafe.exception.ErrorEnum;
import com.leesumin.cafe.exception.MenuException;
import com.leesumin.cafe.menu.interfaces.MenuDto;
import com.leesumin.cafe.menu.domain.Menu;
import com.leesumin.cafe.menu.domain.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public Long createMenu(MenuDto menuDto) {
        validateDuplicate(menuDto.getName());
        Menu menu = Menu.builder()
                .name(menuDto.getName())
                .price(menuDto.getPrice())
                .build();
        menuRepository.save(menu);
        return menu.getId();
    }

    public List<MenuDto> findAllMenu() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(m -> MenuDto.builder()
                    .id(m.getId())
                    .name(m.getName())
                    .price(m.getPrice())
                    .build())
                .collect(Collectors.toList());
    }

    public MenuDto findMenuByName(String name) {
        Menu menu = menuRepository.findByName(name)
                .orElseThrow(() -> new MenuException(ErrorEnum.NOT_FOUND_MENU));
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    public MenuDto findMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorEnum.NOT_FOUND_MENU));
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    private void validateDuplicate(String menuName) {
        if (menuRepository.existsByName(menuName)) {
            throw new MenuException(ErrorEnum.EXISTS_MENU);
        }
    }
}