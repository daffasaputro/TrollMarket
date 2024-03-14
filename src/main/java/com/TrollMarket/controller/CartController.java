package com.TrollMarket.controller;

import com.TrollMarket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping(path = "/index")
    public String index(Model model) {
        var buyerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var cartDataDTO = cartService.getAllData(buyerUsername);
        var cartProductCount = cartDataDTO.size();
        var totalPrice = cartService.getCartTotalPrice(buyerUsername);
        model.addAttribute("cartDataDTO", cartDataDTO);
        model.addAttribute("cartProductCount", cartProductCount);
        model.addAttribute("buyerUsername", buyerUsername);
        model.addAttribute("totalPrice", totalPrice);
        return "/cart/cart-index";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam Integer cartId,
                         @RequestParam String buyerUsername,
                         RedirectAttributes redirectAttributes) {
        cartService.delete(cartId);
        redirectAttributes.addAttribute("buyerUsername", buyerUsername);
        return "redirect:/cart/index";
    }

    @GetMapping(path = "/purchase")
    public String purchase(@RequestParam String buyerUsername,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        var price = cartService.purchase(buyerUsername);

        if (price > 0) {
            redirectAttributes.addAttribute("buyerUsername", buyerUsername);
            return "redirect:/cart/index";
        }

        var cartDataDTO = cartService.getAllData(buyerUsername);
        var cartProductCount = cartDataDTO.size();
        var totalPrice = cartService.getCartTotalPrice(buyerUsername);
        model.addAttribute("cartDataDTO", cartDataDTO);
        model.addAttribute("cartProductCount", cartProductCount);
        model.addAttribute("buyerUsername", buyerUsername);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("price", price.toString().replace("-", ""));
        return "/cart/cart-index";
    }
}
