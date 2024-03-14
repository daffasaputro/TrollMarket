package com.TrollMarket.validation.account;

import com.TrollMarket.dto.account.AccountRegisterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, AccountRegisterDTO> {
    @Override
    public boolean isValid(AccountRegisterDTO accountRegisterDTO, ConstraintValidatorContext constraintValidatorContext) {
        return (accountRegisterDTO.getPassword().equals(accountRegisterDTO.getPasswordConfirmation()));
    }
}
