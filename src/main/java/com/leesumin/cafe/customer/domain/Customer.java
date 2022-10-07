package com.leesumin.cafe.customer.domain;

import lombok.*;

import javax.persistence.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String mobile;
}