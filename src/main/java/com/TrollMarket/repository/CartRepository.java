package com.TrollMarket.repository;

import com.TrollMarket.dto.cart.CartDataDTO;
import com.TrollMarket.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("""
            SELECT new com.TrollMarket.dto.cart.CartDataDTO(car.cartId,
                pro.productId,
                pro.name,
                car.quantity,
                shi.shipperId,
                shi.name,
                acc.username,
                acc.name,
                pro.price,
                pro.price * car.quantity + shi.price)
            FROM Cart AS car
            JOIN car.product AS pro
            JOIN car.shipper AS shi
            JOIN pro.account AS acc
            WHERE car.buyerUsername = :buyerUsername
            """)
    public LinkedList<CartDataDTO> findAllData(@Param("buyerUsername") String buyerUsername);

    @Query("""
            SELECT SUM(pro.price * car.quantity + shi.price)
            FROM Cart AS car
            JOIN car.product AS pro
            JOIN car.shipper AS shi
            JOIN pro.account AS acc
            WHERE car.buyerUsername = :buyerUsername
            """)
    public Double countCartPrice(@Param("buyerUsername") String buyerUsername);

    @Query("""
            SELECT COUNT(car.cartId)
            FROM Cart AS car
            JOIN car.product AS pro
            WHERE pro.productId = :productId
            """)
    public Long countDependenciesToProduct(@Param("productId") Integer productId);

    @Query("""
            SELECT COUNT(car.cartId)
            FROM Cart AS car
            JOIN car.shipper AS shi
            WHERE shi.shipperId = :shipperId
            """)
    public Long countDependenciesToShipper(@Param("shipperId") Integer shipperId);
}
