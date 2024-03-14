package com.TrollMarket.dto.shipper;

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
public class ShipperUpsertDTO {
    private Integer shipperId;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "Name cannot be more than 50 character")
    private String name;

    @NotNull(message = "Name cannot be empty")
    private Double price;

    private Boolean service;
}
