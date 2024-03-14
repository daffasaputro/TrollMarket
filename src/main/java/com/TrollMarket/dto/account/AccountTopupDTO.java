package com.TrollMarket.dto.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountTopupDTO {
    @NotNull(message = "Topup balance cannot be empty")
    @Min(value = 10000, message = "Minimum topup is Rp. 10.000")
    private Double topupBalance;
}
