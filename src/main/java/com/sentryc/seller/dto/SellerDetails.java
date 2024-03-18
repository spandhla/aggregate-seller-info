package com.sentryc.seller.dto;

import com.sentryc.seller.model.SellerInfo;
import java.util.List;
import lombok.Builder;

@Builder
public record SellerDetails(
        String sellerName, String externalId, String marketplaceId, List<ProducerSellerState> producerSellerStates) {

    public static SellerDetails fromSellerInfo(SellerInfo sellerInfo) {
        var seller = sellerInfo.getSeller();
        var producer = seller.getProducer();
        return SellerDetails.builder()
                .sellerName(sellerInfo.getName())
                .externalId(sellerInfo.getExternalId())
                .marketplaceId(sellerInfo.getMarketplace().getId())
                .producerSellerStates(List.of(new ProducerSellerState(
                        producer.getId(), producer.getName(), seller.getState(), seller.getId())))
                .build();
    }
}
