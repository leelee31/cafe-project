package com.leesumin.cafe.menu.interfaces;

import com.leesumin.cafe.menu.application.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menu")
    public ResponseEntity createMenu(@RequestBody MenuDto menuDto) {
        Long menuId = menuService.createMenu(menuDto);
        Map<String, Long> map = new HashMap<>();
        map.put("id", menuId);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuDto>> findAllMenu() {
        List<MenuDto> menus = menuService.findAllMenu();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/menu/{name}")
    public ResponseEntity<MenuDto> findMenuByName(@PathVariable String name) {
        MenuDto menu = menuService.findMenuByName(name);
        return ResponseEntity.ok(menu);
    }
}