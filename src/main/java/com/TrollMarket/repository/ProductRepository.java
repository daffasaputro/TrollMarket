package com.TrollMarket.repository;

import com.TrollMarket.dto.product.ProductDataDTO;
import com.TrollMarket.dto.product.ProductDetailDTO;
import com.TrollMarket.dto.product.SellerProductDataDTO;
import com.TrollMarket.dto.product.SellerProductDetailDTO;
import com.TrollMarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
            SELECT new com.TrollMarket.dto.product.ProductDataDTO(pro.productId,
                pro.name,
                pro.price,
                pro.sellerUsername)
            FROM Product AS pro
            WHERE pro.discontinue = false AND
                (:productName IS NULL OR pro.name LIKE %:productName%) AND
                (:productCategory IS NULL OR pro.category LIKE %:productCategory%) AND
                (:productDescription IS NULL OR pro.description LIKE %:productDescription%)
            ORDER BY pro.name
            """)
    public Page<ProductDataDTO> findAllData(@Param("productName") String productName,
                                            @Param("productCategory") String productCategory,
                                            @Param("productDescription") String productDescription,
                                            Pageable pageable);

    @Query("""
            SELECT new com.TrollMarket.dto.product.ProductDetailDTO(pro.name,
                pro.category,
                pro.description,
                pro.price,
                acc.name)
            FROM Product AS pro
            JOIN pro.account AS acc
            WHERE pro.productId = :productId
            """)
    public ProductDetailDTO productDetail(@Param("productId") Integer productId);

    @Query("""
            SELECT new com.TrollMarket.dto.product.SellerProductDataDTO(pro.productId,
                pro.name,
                pro.category,
                pro.discontinue)
            FROM Product AS pro
            WHERE pro.sellerUsername = :sellerUsername
            """)
    public Page<SellerProductDataDTO> sellerProduct(@Param("sellerUsername") String username,
                                                    Pageable pageable);

    @Query("""
            SELECT new com.TrollMarket.dto.product.SellerProductDetailDTO(pro.productId,
                pro.name,
                pro.category,
                pro.description,
                pro.price,
                pro.discontinue)
            FROM Product AS pro
            WHERE pro.productId = :productId
            """)
    public SellerProductDetailDTO sellerProductDetail(@Param("productId") Integer productId);
}
