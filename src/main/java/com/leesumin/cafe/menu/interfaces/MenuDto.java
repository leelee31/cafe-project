package com.leesumin.cafe.menu.interfaces;

import com.leesumin.cafe.menu.domain.Menu;
import lombok.Data;

@Data
public class MenuDto {
    private Long id;
    private String name;
    private Integer price;

    public MenuDto (String name, int price) {
        this.name = name;
        this.price = price;
    }

    public MenuDto (Menu menu) {
        id = menu.getId();
        name = menu.getName();
        price = menu.getPrice();
    }
}