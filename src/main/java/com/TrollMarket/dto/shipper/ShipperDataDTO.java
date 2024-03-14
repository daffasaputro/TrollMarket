package com.TrollMarket.dto.shipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShipperDataDTO {
    private Integer shipperId;
    private String name;
    private Double price;
    private Boolean service;
    private Long cartDependencies;
    private Long orderDependencies;

    public ShipperDataDTO(Integer shipperId, String name, Double price, Boolean service) {
        this.shipperId = shipperId;
        this.name = name;
        this.price = price;
        this.service = service;
    }
}
