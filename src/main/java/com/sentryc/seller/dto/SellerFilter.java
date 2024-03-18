package com.sentryc.seller.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record SellerFilter(String searchByName, List<String> marketplaceIds, List<UUID> producerIds) {}
