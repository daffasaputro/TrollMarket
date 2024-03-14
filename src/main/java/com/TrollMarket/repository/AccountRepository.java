package com.TrollMarket.repository;

import com.TrollMarket.dto.account.AccountProfileDTO;
import com.TrollMarket.dto.utility.DropdownDTO;
import com.TrollMarket.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("""
            SELECT new com.TrollMarket.dto.account.AccountProfileDTO(acc.name,
                acc.role,
                acc.address,
                acc.balance)
            FROM Account AS acc
            WHERE acc.username = :username
            """)
    public AccountProfileDTO accountProfile(@Param("username") String username);

    @Query("""
            SELECT new com.TrollMarket.dto.utility.DropdownDTO(acc.username,
                acc.name)
            FROM Account AS acc
            WHERE acc.role = 'Seller'
            """)
    public LinkedList<DropdownDTO> sellerDropdown();

    @Query("""
            SELECT new com.TrollMarket.dto.utility.DropdownDTO(acc.username,
                acc.name)
            FROM Account AS acc
            WHERE acc.role = 'Buyer'
            """)
    public LinkedList<DropdownDTO> buyerDropdown();

    @Query("""
            SELECT COUNT(acc.username)
            FROM Account AS acc
            WHERE acc.username = :username
            """)
    public Long countExsistingUsername(@Param("username") String username);
}
