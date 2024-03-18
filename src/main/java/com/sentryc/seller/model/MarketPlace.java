package com.sentryc.seller.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "marketplaces")
@Entity
public class MarketPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "marketplace", orphanRemoval = true)
    private Set<SellerInfo> sellerInfos;
}
