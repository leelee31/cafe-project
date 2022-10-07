package com.leesumin.cafe.menu.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "menu")
@Entity
public class Menu {
    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;
}