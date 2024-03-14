package com.TrollMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Carts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Cart {
    @Id
    @Column(name = "CartId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

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
    private Integer shipperId;

    @ManyToOne
    @JoinColumn(name = "ShipperId", insertable = false, updatable = false)
    private Shipper shipper;

    @Column(name = "Quantity")
    private Integer quantity;
}
