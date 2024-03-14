package com.TrollMarket.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SellerProductDetailDTO {
    private Integer productId;
    private String name;
    private String category;
    private String description;
    private Double price;
    private Boolean discontinue;
}
