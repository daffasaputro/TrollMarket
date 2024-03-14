package com.TrollMarket.controller;

import com.TrollMarket.dto.account.AccountRegisterDTO;
import com.TrollMarket.dto.account.AccountTopupDTO;
import com.TrollMarket.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        Model model) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var accountDetailDTO = accountService.getAccountDetail(username, page);
        var totalPage = accountDetailDTO.getAccountTransactionHistory().getTotalPages();
        model.addAttribute("accountDetailDTO", accountDetailDTO);
        model.addAttribute("username", username);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/account/account-index";
    }

    @PostMapping("/topup/{username}")
    public ResponseEntity<Object> topup(@PathVariable String username,
                                        @Valid @RequestBody AccountTopupDTO accountTopupDTO,
                                        BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            accountService.topup(username, accountTopupDTO);
            return ResponseEntity.status(200).body("Success topup");
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        return "/account/login-form";
    }

    @GetMapping("/registerForm")
    public String registerForm(@RequestParam String role,
                               Model model) {
        var accountRegisterDTO = new AccountRegisterDTO();
        accountRegisterDTO.setRole(role);
        model.addAttribute("accountRegisterDTO", accountRegisterDTO);
        return "/account/register-form";
    }

    @PostMapping(path = "/register")
    public String register(@Valid @ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/account/register-form";
        }

        accountService.register(accountRegisterDTO);
        return "redirect:/account/loginForm";
    }

    @GetMapping("/adminRegisterForm")
    public String adminRegisterForm(Model model) {
        var accountRegisterDTO = new AccountRegisterDTO();
        accountRegisterDTO.setRole("Admin");
        accountRegisterDTO.setName("-");
        accountRegisterDTO.setAddress("-");
        model.addAttribute("accountRegisterDTO", accountRegisterDTO);
        return "/account/admin-register-form";
    }

    @PostMapping(path = "/adminRegister")
    public String adminRegister(@Valid @ModelAttribute("accountRegisterDTO") AccountRegisterDTO accountRegisterDTO,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/account/admin-register-form";
        }

        accountService.register(accountRegisterDTO);
        return "redirect:/shipper/index";
    }
}
