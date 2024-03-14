package com.TrollMarket.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountDetailDTO {
    private AccountProfileDTO accountProfile;
    private Page<AccountTransactionHistoryDTO> accountTransactionHistory;
}
