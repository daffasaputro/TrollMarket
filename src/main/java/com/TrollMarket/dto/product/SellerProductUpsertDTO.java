package com.TrollMarket.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SellerProductUpsertDTO {
    private Integer productId;

    private String sellerUsername;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "Name cannot be more than 50 characters")
    private String name;

    @Size(max = 50, message = "Description cannot be more than 50 characters")
    private String category;

    @Size(max = 150, message = "Description cannot be more than 150 characters")
    private String description;

    @NotNull(message = "Price cannot be empty")
    private Double price;

    private Boolean discontinue;
}
