package com.leesumin.cafe.menu.application;

import com.leesumin.cafe.exception.EntityDuplicationException;
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
        Menu menu = menuRepository.findByName(name).orElseThrow(() -> new IllegalStateException("해당 메뉴 정보가 없습니다")        );
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    public MenuDto findMenuById(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 메뉴 정보가 없습니다")        );
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    private void validateDuplicate(String menuName) {
        boolean isDuplicate = menuRepository.existsByName(menuName);
        if (isDuplicate) {
            throw new EntityDuplicationException("이미 존재하는 메뉴명입니다.");
        }
    }
}