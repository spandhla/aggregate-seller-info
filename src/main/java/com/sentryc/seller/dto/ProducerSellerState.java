package com.sentryc.seller.dto;

import com.sentryc.seller.model.SellerState;
import java.util.UUID;

public record ProducerSellerState(UUID producerId, String producerName, SellerState sellerState, UUID sellerId) {}
