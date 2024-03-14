package com.TrollMarket.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SellerProductDataDTO {
    private Integer productId;
    private String name;
    private String category;
    private Boolean discontinue;
    private Long cartDependencies;
    private Long orderDependencies;

    public SellerProductDataDTO(Integer productId, String name, String category, Boolean discontinue) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.discontinue = discontinue;
    }
}
