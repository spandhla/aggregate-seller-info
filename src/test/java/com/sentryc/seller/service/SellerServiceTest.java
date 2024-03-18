package com.sentryc.seller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sentryc.seller.dto.PageInput;
import com.sentryc.seller.dto.SellerFilter;
import com.sentryc.seller.dto.SellerSortBy;
import com.sentryc.seller.model.*;
import com.sentryc.seller.repository.SellerInfoSearchDao;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SellerServiceTest {

    @Mock
    private SellerInfoSearchDao sellerInfoSearchDao;

    @InjectMocks
    SellerService sellerService;

    SellerInfo sellerInfo;

    @BeforeEach
    void setUp() {
        var producer = new Producer(UUID.randomUUID(), "Nike", LocalDateTime.now(), null);
        var marketplace =
                new MarketPlace("8d2bc39e-3ba3-4bdb-a45d-00865d4bad95", "market place 1", Collections.emptySet());
        var seller = new Seller(UUID.randomUUID(), null, producer, SellerState.REGULAR);
        sellerInfo =
                new SellerInfo(UUID.randomUUID(), "John", "https://example.com", "DE", marketplace, "ext_id_1", seller);
    }

    @Test
    void getSellers() {
        var filter = SellerFilter.builder().searchByName("John").build();
        var page = new PageInput(0, 10);
        when(sellerInfoSearchDao.findAll(any(SellerFilter.class), any(PageInput.class), any(SellerSortBy.class)))
                .thenReturn(List.of(sellerInfo));

        var sellerDetails = sellerService.getSellers(filter, page, SellerSortBy.NAME_ASC);

        assertThat(sellerDetails).isNotNull();
        assertThat(sellerDetails.data().size()).isEqualTo(1);
        assertThat(sellerDetails.data().get(0).sellerName()).isEqualTo("John");
        assertThat(sellerDetails.data().get(0).externalId()).isEqualTo("ext_id_1");
        assertThat(sellerDetails.data().get(0).marketplaceId()).isEqualTo("8d2bc39e-3ba3-4bdb-a45d-00865d4bad95");
        assertThat(sellerDetails.data().get(0).producerSellerStates().size()).isEqualTo(1);
        assertThat(sellerDetails.data().get(0).producerSellerStates().get(0).sellerState())
                .isEqualTo(SellerState.REGULAR);
        assertThat(sellerDetails.data().get(0).producerSellerStates().get(0).producerName())
                .isEqualTo("Nike");
        assertThat(sellerDetails.meta().pageSize()).isEqualTo(1);
        assertThat(sellerDetails.meta().currentPage()).isEqualTo(0);
    }

    @Test
    void getSellers_empty() {
        var filter = SellerFilter.builder().searchByName("John").build();
        var page = new PageInput(0, 10);
        when(sellerInfoSearchDao.findAll(any(SellerFilter.class), any(PageInput.class), any(SellerSortBy.class)))
                .thenReturn(List.of());

        var sellerDetails = sellerService.getSellers(filter, page, SellerSortBy.NAME_ASC);

        assertThat(sellerDetails).isNotNull();
        assertThat(sellerDetails.data().size()).isEqualTo(0);
    }
}
