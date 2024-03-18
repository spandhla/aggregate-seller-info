package com.sentryc.seller.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record SellerPageableResponse(PagedMeta meta, List<SellerDetails> data) {}
