package com.TrollMarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CartDataDTO {
    private Integer cartId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer shipperId;
    private String shipperName;
    private String sellerUsername;
    private String sellerName;
    private Double productPrice;
    private Double totalPrice;
}
