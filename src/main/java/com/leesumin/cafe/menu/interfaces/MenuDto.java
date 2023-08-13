package com.leesumin.cafe.menu.interfaces;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Data
public class MenuDto {
    private Long id;
    private String name;
    private Integer price;
}