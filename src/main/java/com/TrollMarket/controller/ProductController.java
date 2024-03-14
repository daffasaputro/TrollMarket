package com.TrollMarket.controller;

import com.TrollMarket.dto.product.ProductAddToCartDTO;
import com.TrollMarket.dto.product.SellerProductUpsertDTO;
import com.TrollMarket.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/shop")
    public String shop(@RequestParam(defaultValue = "") String productName,
                       @RequestParam(defaultValue = "") String productCategory,
                       @RequestParam(defaultValue = "") String productDescription,
                       @RequestParam(defaultValue = "1") Integer page,
                       Model model) {
        var productDataDTO = productService.getAllData(productName,
                productCategory,
                productDescription,
                page);
        var totalPage = productDataDTO.getTotalPages();
        model.addAttribute("productDataDTO", productDataDTO);
        model.addAttribute("productName", productName);
        model.addAttribute("productCategory", productCategory);
        model.addAttribute("productDescription", productDescription);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/product/product-shop";
    }

    @GetMapping(path = "/detail/{productId}")
    public ResponseEntity<Object> getProductDetail(@PathVariable Integer productId) {
        try {
            var productDetailDTO = productService.getProductDetail(productId);
            return ResponseEntity.status(200).body(productDetailDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/shipperDropdown")
    public ResponseEntity<Object> getShipperDropdown() {
        try {
            var shipperDropdownDTO = productService.getShipperDropdown();
            return ResponseEntity.status(200).body(shipperDropdownDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PostMapping(path = "/addToCart")
    public ResponseEntity<Object> addToCart(@Valid @RequestBody ProductAddToCartDTO productAddToCartDTO,
                                            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            productService.addToCart(productAddToCartDTO);
            return ResponseEntity.status(200).body(productAddToCartDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/merchandise")
    public String merchandise(@RequestParam(defaultValue = "1") Integer page,
                              Model model) {
        var sellerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var sellerProductDataDTO = productService.getSellerProduct(sellerUsername, page);
        var totalPage = sellerProductDataDTO.getTotalPages();
        model.addAttribute("sellerProductDataDTO", sellerProductDataDTO);
        model.addAttribute("sellerUsername", sellerUsername);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/product/product-merchandise";
    }

    @GetMapping(path = "/merchandise/insertForm")
    public String merchandiseInsertForm(Model model) {
        var sellerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var sellerProductUpsertDTO = new SellerProductUpsertDTO();
        sellerProductUpsertDTO.setSellerUsername(sellerUsername);
        model.addAttribute("sellerProductUpsertDTO", sellerProductUpsertDTO);
        return "/product/product-merchandise-form";
    }

    @GetMapping(path = "/merchandise/updateForm")
    public String merchandiseUpdateForm(@RequestParam Integer productId,
                                        Model model) {
        var sellerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        var sellerProductUpsertDTO = productService.getOneSellerProduct(productId);
        sellerProductUpsertDTO.setProductId(productId);
        sellerProductUpsertDTO.setSellerUsername(sellerUsername);
        model.addAttribute("sellerProductUpsertDTO", sellerProductUpsertDTO);
        return "/product/product-merchandise-form";
    }

    @PostMapping(path = "/merchandise/upsert")
    public String merchandiseUpsert(@Valid @ModelAttribute("sellerProductUpsertDTO")
                                    SellerProductUpsertDTO sellerProductUpsertDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/product/product-merchandise-form";
        }

        productService.sellerProductUpsert(sellerProductUpsertDTO);
        redirectAttributes.addAttribute("sellerUsername", sellerProductUpsertDTO.getSellerUsername());
        return "redirect:/product/merchandise";
    }

    @GetMapping(path = "/merchandise/delete")
    public String merchandiseDelete(Integer productId,
                                    RedirectAttributes redirectAttributes) {
        productService.delete(productId);
        return "redirect:/product/merchandise";
    }

    @GetMapping(path = "/merchandise/discontinue")
    public String merchandiseDiscontinue(Integer productId,
                                         RedirectAttributes redirectAttributes) {
        productService.discontinue(productId);
        return "redirect:/product/merchandise";
    }

    @GetMapping(path = "/merchandise/productDetail/{productId}")
    public ResponseEntity<Object> merchandiseDetail(@PathVariable Integer productId) {
        try {
            var sellerProductDetailDTO = productService.getSellerProductDetail(productId);
            return ResponseEntity.status(200).body(sellerProductDetailDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }
}
