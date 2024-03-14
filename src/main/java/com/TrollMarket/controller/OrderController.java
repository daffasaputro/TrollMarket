package com.TrollMarket.controller;

import com.TrollMarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/index")
    public String index(@RequestParam(required = false) String sellerUsername,
                        @RequestParam(required = false) String buyerUsername,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model) {
        var orderDataDTO = orderService.getAllData(sellerUsername, buyerUsername, page);
        var sellerDropdownDTO = orderService.getSellerDropdown();
        var buyerDropdownDTO = orderService.getBuyerDropdown();
        var totalPage = orderDataDTO.getTotalPages();
        model.addAttribute("orderDataDTO", orderDataDTO);
        model.addAttribute("sellerDropdownDTO", sellerDropdownDTO);
        model.addAttribute("buyerDropdownDTO", buyerDropdownDTO);
        model.addAttribute("sellerUsername", sellerUsername);
        model.addAttribute("buyerUsername", buyerUsername);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/order/order-index";
    }
}
