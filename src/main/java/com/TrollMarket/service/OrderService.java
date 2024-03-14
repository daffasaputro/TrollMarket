package com.TrollMarket.service;

import com.TrollMarket.dto.order.OrderDataDTO;
import com.TrollMarket.dto.utility.DropdownDTO;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Page<OrderDataDTO> getAllData(String sellerusername, String buyerUsername, Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var orderDataDTO = orderRepository.findAllData(sellerusername, buyerUsername, pageable);
        return orderDataDTO;
    }

    public LinkedList<DropdownDTO> getSellerDropdown() {
        var sellerDropdownDTO = accountRepository.sellerDropdown();
        return sellerDropdownDTO;
    }

    public LinkedList<DropdownDTO> getBuyerDropdown() {
        var buyerDropdownDTO = accountRepository.buyerDropdown();
        return buyerDropdownDTO;
    }
}
