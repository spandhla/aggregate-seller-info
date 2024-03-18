package com.sentryc.seller.repository;

import com.sentryc.seller.model.SellerInfo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoRepository extends JpaRepository<SellerInfo, UUID> {}
