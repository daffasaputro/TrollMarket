package com.TrollMarket.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountTransactionHistoryDTO {
    private LocalDate orderDate;
    private String product;
    private Integer quantity;
    private String shipper;
    private Double price;
}
