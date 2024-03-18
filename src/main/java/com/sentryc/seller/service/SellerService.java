package com.sentryc.seller.service;

import com.sentryc.seller.dto.*;
import com.sentryc.seller.repository.SellerInfoSearchDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerInfoSearchDao sellerInfoSearchDao;

    public SellerPageableResponse getSellers(SellerFilter filter, PageInput page, SellerSortBy sortBy) {
        var data = sellerInfoSearchDao.findAll(filter, page, sortBy);
        var meta = new PagedMeta(data.size(), page.page());
        var sellersDto = data.stream()
                .map(sellerInfo -> SellerDetails.fromSellerInfo(sellerInfo))
                .toList();

        return SellerPageableResponse.builder().meta(meta).data(sellersDto).build();
    }
}
