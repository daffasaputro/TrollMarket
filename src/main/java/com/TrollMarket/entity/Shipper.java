package com.TrollMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Shippers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Shipper {
    @Id
    @Column(name = "ShipperId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipperId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Service")
    private Boolean service;
}
