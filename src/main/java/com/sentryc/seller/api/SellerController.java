package com.sentryc.seller.api;

import com.sentryc.seller.dto.PageInput;
import com.sentryc.seller.dto.SellerFilter;
import com.sentryc.seller.dto.SellerPageableResponse;
import com.sentryc.seller.dto.SellerSortBy;
import com.sentryc.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class SellerController {

    private final SellerService sellerService;

    @QueryMapping
    public SellerPageableResponse sellers(
            @Argument SellerFilter filter, @Argument PageInput page, @Argument SellerSortBy sortBy) {
        return sellerService.getSellers(filter, page, sortBy);
    }
}
