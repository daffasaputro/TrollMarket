package com.TrollMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @Column(name = "OrderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "ProductId")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false, updatable = false)
    private Product product;

    @Column(name = "BuyerUsername")
    private String buyerUsername;

    @ManyToOne
    @JoinColumn(name = "BuyerUsername", insertable = false, updatable = false)
    private Account account;

    @Column(name = "ShipperId")
    private Integer ShipperId;

    @ManyToOne
    @JoinColumn(name = "ShipperId", insertable = false, updatable = false)
    private Shipper shipper;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private Double price;

    @Column(name = "OrderDate")
    private LocalDate orderDate;
}
