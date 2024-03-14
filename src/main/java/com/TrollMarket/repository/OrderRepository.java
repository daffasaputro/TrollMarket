package com.TrollMarket.repository;

import com.TrollMarket.dto.account.AccountTransactionHistoryDTO;
import com.TrollMarket.dto.order.OrderDataDTO;
import com.TrollMarket.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("""
            SELECT new com.TrollMarket.dto.account.AccountTransactionHistoryDTO(ord.orderDate,
                pro.name,
                ord.quantity,
                shi.name,
                ord.price)
            FROM Order AS ord
            JOIN ord.product AS pro
            JOIN ord.shipper AS shi
            WHERE ord.buyerUsername = :username OR pro.sellerUsername = :username
            """)
    public Page<AccountTransactionHistoryDTO> accountTransactionHistory(@Param("username") String username,
                                                                        Pageable pageable);

    @Query("""
            SELECT new com.TrollMarket.dto.order.OrderDataDTO(ord.orderDate,
                sel.name,
                buy.name,
                pro.name,
                ord.quantity,
                shi.name,
                ord.price)
            FROM Order AS ord
            JOIN ord.product AS pro
            JOIN ord.account AS buy
            JOIN pro.account AS sel
            JOIN ord.shipper AS shi
            WHERE (:sellerUsername IS NULL OR sel.username LIKE %:sellerUsername%) AND 
                (:buyerUsername IS NULL OR buy.username LIKE %:buyerUsername%) 
            """)
    public Page<OrderDataDTO> findAllData(@Param("sellerUsername") String sellerUsername,
                                          @Param("buyerUsername") String buyerUsername,
                                          Pageable pageable);

    @Query("""
            SELECT COUNT(ord.orderId)
            FROM Order AS ord
            JOIN ord.product AS pro
            WHERE pro.productId = :productId
            """)
    public Long countDependenciesToProduct(@Param("productId") Integer productId);

    @Query("""
            SELECT COUNT(ord.orderId)
            FROM Order AS ord
            JOIN ord.shipper AS shi
            WHERE shi.shipperId = :shipperId
            """)
    public Long countDependenciesToShipper(@Param("shipperId") Integer shipperId);
}
