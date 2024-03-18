package com.sentryc.seller.dto;

import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

public enum SellerSortBy {
    SELLER_INFO_EXTERNAL_ID_ASC,
    SELLER_INFO_EXTERNAL_ID_DESC,
    NAME_ASC,
    NAME_DESC,
    MARKETPLACE_ID_ASC,
    MARKETPLACE_ID_DESC;

    public Pair<String, Sort.Direction> getSortBy() {
        return switch (this) {
            case NAME_ASC -> Pair.of("name", Sort.Direction.ASC);
            case NAME_DESC -> Pair.of("name", Sort.Direction.DESC);
            case MARKETPLACE_ID_ASC -> Pair.of("id", Sort.Direction.ASC);
            case MARKETPLACE_ID_DESC -> Pair.of("id", Sort.Direction.DESC);
            case SELLER_INFO_EXTERNAL_ID_ASC -> Pair.of("externalId", Sort.Direction.ASC);
            case SELLER_INFO_EXTERNAL_ID_DESC -> Pair.of("externalId", Sort.Direction.DESC);
        };
    }
}
