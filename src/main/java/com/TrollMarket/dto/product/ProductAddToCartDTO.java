package com.TrollMarket.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductAddToCartDTO {
    private Integer cartId;

    private Integer productId;

    private String buyerUsername;

    @NotNull(message = "Shipper cannot be empty")
    private Integer shipperId;

    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;
}
