package com.TrollMarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderDataDTO {
    private LocalDate orderDate;
    private String seller;
    private String buyer;
    private String product;
    private Integer quantity;
    private String shipper;
    private Double price;
}
