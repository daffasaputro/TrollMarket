package com.TrollMarket.dto.account;

import com.TrollMarket.validation.account.ConfirmPassword;
import com.TrollMarket.validation.account.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ConfirmPassword(message = "Confirm password does not match")
public class AccountRegisterDTO {
    @NotBlank(message = "Username cannot be empty")
    @Size(max = 50, message = "Username cannot be more than 50 characters")
    @UniqueUsername(message = "Username already exsist")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(max = 50, message = "Password cannot be more than 50 characters")
    private String password;

    private String passwordConfirmation;

    private String role;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "Name cannot be more than 50 characters")
    private String name;

    @NotBlank(message = "Address cannot be empty")
    @Size(max = 150, message = "Address cannot be more than 150 characters")
    private String address;
}
