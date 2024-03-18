package com.sentryc.seller.repository;

import com.sentryc.seller.model.Seller;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, UUID> {}
