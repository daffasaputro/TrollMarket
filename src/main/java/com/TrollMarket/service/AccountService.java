package com.TrollMarket.service;

import com.TrollMarket.dto.account.*;
import com.TrollMarket.entity.Account;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountDetailDTO getAccountDetail(String username, Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var accountProfileDTO = accountRepository.accountProfile(username);
        var accountTransactionHistoryDTO = orderRepository.accountTransactionHistory(
                username,
                pageable);
        var accountDetailDTO = new AccountDetailDTO(accountProfileDTO, accountTransactionHistoryDTO);
        return accountDetailDTO;
    }

    public void topup(String username, AccountTopupDTO accountTopupDTO) {
        var account = accountRepository.findById(username).get();
        account.setBalance(account.getBalance() + accountTopupDTO.getTopupBalance());
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository.findById(username).get();
        if(account == null){
            throw new UsernameNotFoundException("User not found");
        } else {
            var userDetail = new ApplicationUserDetails(account);
            return userDetail;
        }
    }

    public Boolean isUsernameAvailable(String username) {
        var count = accountRepository.countExsistingUsername(username);
        return (count > 0) ? false : true;
    }

    public void register(AccountRegisterDTO accountRegisterDTO) {
        var hashedPassword = passwordEncoder.encode(accountRegisterDTO.getPassword());
        var account = new Account();
        account.setUsername(accountRegisterDTO.getUsername());
        account.setPassword(hashedPassword);
        account.setRole(accountRegisterDTO.getRole());
        account.setName(accountRegisterDTO.getName());
        account.setAddress(accountRegisterDTO.getAddress());
        account.setBalance(0d);
        accountRepository.save(account);
    }
}
