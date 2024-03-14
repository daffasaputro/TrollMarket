package com.TrollMarket.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountProfileDTO {
    private String name;
    private String role;
    private String address;
    private Double balance;
}
