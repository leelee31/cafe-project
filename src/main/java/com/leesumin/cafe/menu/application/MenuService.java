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

import static java.util.stream.Collectors.toList;

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
                .map(MenuDto::new)
                .collect(toList());
    }

    public MenuDto findMenuByName(String name) {
        Menu menu = menuRepository.findByName(name)
                .orElseThrow(() -> new MenuException(ErrorEnum.NOT_FOUND_MENU));
        return new MenuDto(menu);
    }

    public MenuDto findMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorEnum.NOT_FOUND_MENU));
        return new MenuDto(menu);
    }

    private void validateDuplicate(String menuName) {
        if (menuRepository.existsByName(menuName)) {
            throw new MenuException(ErrorEnum.EXISTS_MENU);
        }
    }
}