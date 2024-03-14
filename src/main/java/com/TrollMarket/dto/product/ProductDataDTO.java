package com.TrollMarket.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDataDTO {
    private Integer productId;
    private String name;
    private Double price;
    private String sellerUsername;
}
