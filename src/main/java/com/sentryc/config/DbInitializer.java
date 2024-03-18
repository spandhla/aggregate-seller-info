package com.sentryc.config;

import com.sentryc.seller.model.*;
import com.sentryc.seller.repository.MarketPlaceRepository;
import com.sentryc.seller.repository.ProducerRepository;
import com.sentryc.seller.repository.SellerInfoRepository;
import com.sentryc.seller.repository.SellerRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DbInitializer implements ApplicationRunner {
    private final MarketPlaceRepository marketPlaceRepository;
    private final ProducerRepository producerRepository;
    private final SellerInfoRepository sellerInfoRepository;
    private final SellerRepository sellerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var producers = List.of(
                new Producer(null, "Nike", LocalDateTime.now(), null),
                new Producer(null, "Adidas", LocalDateTime.now(), null));
        producerRepository.saveAll(producers);
        var marketPlaces = List.of(
                new MarketPlace(null, "market place 1", Collections.emptySet()),
                new MarketPlace(null, "market place 2", Collections.emptySet()));
        marketPlaceRepository.saveAll(marketPlaces);

        var sellerInfos = List.of(
                new SellerInfo(null, "John", "https://example.com", "DE", marketPlaces.get(0), "ext_id_1", null),
                new SellerInfo(null, "Sarah", "https://example2.com", "DE", marketPlaces.get(1), "ext_id_2", null));
        sellerInfoRepository.saveAll(sellerInfos);

        var sellers = List.of(
                new Seller(null, sellerInfos.get(0), producers.get(0), SellerState.REGULAR),
                new Seller(null, sellerInfos.get(1), producers.get(1), SellerState.BLACKLISTED));
        sellerRepository.saveAll(sellers);
    }
}
