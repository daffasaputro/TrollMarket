package com.TrollMarket.service;

import com.TrollMarket.dto.product.*;
import com.TrollMarket.dto.utility.DropdownDTO;
import com.TrollMarket.entity.Cart;
import com.TrollMarket.entity.Product;
import com.TrollMarket.repository.CartRepository;
import com.TrollMarket.repository.OrderRepository;
import com.TrollMarket.repository.ProductRepository;
import com.TrollMarket.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<ProductDataDTO> getAllData(String productName,
                                           String productCategory,
                                           String productDescription,
                                           Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var productDataDTO = productRepository.findAllData(productName,
                productCategory,
                productDescription,
                pageable);
        return productDataDTO;
    }

    public ProductDetailDTO getProductDetail(Integer productId) {
        var productDetailDTO = productRepository.productDetail(productId);
        return productDetailDTO;
    }

    public LinkedList<DropdownDTO> getShipperDropdown() {
        var shipperDropdownDTO = shipperRepository.shipperDropdown();
        return shipperDropdownDTO;
    }

    public void addToCart(ProductAddToCartDTO productAddToCartDTO) {
        var cart = new Cart();
        cart.setProductId(productAddToCartDTO.getProductId());
        cart.setBuyerUsername(productAddToCartDTO.getBuyerUsername());
        cart.setShipperId(productAddToCartDTO.getShipperId());
        cart.setQuantity(productAddToCartDTO.getQuantity());
        cartRepository.save(cart);
    }

    public Page<SellerProductDataDTO> getSellerProduct(String sellerUsername, Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var sellerProductDataDTO = productRepository.sellerProduct(sellerUsername, pageable);

        for (SellerProductDataDTO product : sellerProductDataDTO) {
            product.setCartDependencies(cartRepository.countDependenciesToProduct(product.getProductId()));
            product.setOrderDependencies(orderRepository.countDependenciesToProduct(product.getProductId()));
        }

        return sellerProductDataDTO;
    }

    public SellerProductUpsertDTO getOneSellerProduct(Integer productId) {
        var product = productRepository.findById(productId).get();
        var sellerProductUpsertDTO = new SellerProductUpsertDTO();
        sellerProductUpsertDTO.setProductId(product.getProductId());
        sellerProductUpsertDTO.setSellerUsername(product.getSellerUsername());
        sellerProductUpsertDTO.setName(product.getName());
        sellerProductUpsertDTO.setCategory(product.getCategory());
        sellerProductUpsertDTO.setDescription(product.getDescription());
        sellerProductUpsertDTO.setPrice(product.getPrice());
        sellerProductUpsertDTO.setDiscontinue(product.getDiscontinue());
        return sellerProductUpsertDTO;
    }

    public void sellerProductUpsert(SellerProductUpsertDTO sellerProductUpsertDTO) {
        var product = new Product();
        product.setProductId(sellerProductUpsertDTO.getProductId());
        product.setSellerUsername(sellerProductUpsertDTO.getSellerUsername());
        product.setName(sellerProductUpsertDTO.getName());
        product.setCategory(sellerProductUpsertDTO.getCategory());
        product.setDescription(sellerProductUpsertDTO.getDescription());
        product.setPrice(sellerProductUpsertDTO.getPrice());
        product.setDiscontinue(sellerProductUpsertDTO.getDiscontinue());
        productRepository.save(product);
    }

    public void delete(Integer productId) {
        productRepository.deleteById(productId);
    }

    public void discontinue(Integer productId) {
        var product = productRepository.findById(productId).get();
        product.setDiscontinue(true);
        productRepository.save(product);
    }

    public SellerProductDetailDTO getSellerProductDetail(Integer productId) {
        var sellerProductDetailDTO = productRepository.sellerProductDetail(productId);
        return sellerProductDetailDTO;
    }
}
