package com.sentryc.seller.repository;

import com.sentryc.seller.model.MarketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketPlaceRepository extends JpaRepository<MarketPlace, String> {}
