package com.leesumin.cafe.menu.interfaces;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MenuDto {
    private final Long id;
    private final String name;
    private final Integer price;
}