package com.TrollMarket.service;

import com.TrollMarket.dto.cart.CartDataDTO;
import com.TrollMarket.entity.Order;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.repository.CartRepository;
import com.TrollMarket.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Double getCartTotalPrice(String buyerUsername) {
        var totalPrice = cartRepository.countCartPrice(buyerUsername);
        return totalPrice;
    }

    public LinkedList<CartDataDTO> getAllData(String buyerUsername) {
        var cartDataDTO = cartRepository.findAllData(buyerUsername);
        return cartDataDTO;
    }

    public void delete(Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    public Double purchase(String buyerUsername) {
        var cartDataDTO = cartRepository.findAllData(buyerUsername);
        var price = cartRepository.countCartPrice(buyerUsername);
        var buyerAccount = accountRepository.findById(buyerUsername).get();

        if (buyerAccount.getBalance() > price) {
            for (CartDataDTO cart : cartDataDTO) {
                var order = new Order();
                order.setProductId(cart.getProductId());
                order.setBuyerUsername(buyerUsername);
                order.setShipperId(cart.getShipperId());
                order.setQuantity(cart.getQuantity());
                order.setPrice(cart.getTotalPrice());
                order.setOrderDate(LocalDate.now());
                orderRepository.save(order);
                var seller = accountRepository.findById(cart.getSellerUsername()).get();
                seller.setBalance(seller.getBalance() + (cart.getProductPrice() * cart.getQuantity()));
                accountRepository.save(seller);
                cartRepository.deleteById(cart.getCartId());
            }

            buyerAccount.setBalance(buyerAccount.getBalance() - price);
            accountRepository.save(buyerAccount);
            return 0d;
        }

        return buyerAccount.getBalance() - price;
    }
}
