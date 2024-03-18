package com.sentryc.seller.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sellers")
@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "seller_info_id")
    private SellerInfo sellerInfo;

    @OneToOne
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @Column(columnDefinition = "varchar(255) default 'REGULAR'", nullable = false)
    @Enumerated(EnumType.STRING)
    private SellerState state;
}
