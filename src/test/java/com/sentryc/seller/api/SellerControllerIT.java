package com.sentryc.seller.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.sentryc.common.AbstractIntegrationTest;
import com.sentryc.seller.dto.SellerPageableResponse;
import com.sentryc.seller.model.MarketPlace;
import com.sentryc.seller.model.Producer;
import com.sentryc.seller.model.SellerState;
import com.sentryc.seller.repository.MarketPlaceRepository;
import com.sentryc.seller.repository.ProducerRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SellerControllerIT extends AbstractIntegrationTest {

    @Autowired
    private MarketPlaceRepository marketPlaceRepository;

    @Autowired
    private ProducerRepository producerRepository;

    List<String> marketplaceIds;
    List<UUID> producerIds;

    @BeforeEach
    void setUp() {
        marketplaceIds =
                marketPlaceRepository.findAll().stream().map(MarketPlace::getId).toList();
        producerIds = producerRepository.findAll().stream().map(Producer::getId).toList();
    }

    @Test
    void shouldReturnAllSellerDetails_Sorted() {
        var response = httpGraphQlTester
                .documentName("sellerDetails")
                .variable("filter", null)
                .variable("sortBy", "NAME_DESC")
                .variable("page", Map.of("page", 0, "size", 2))
                .execute()
                .errors()
                .verify()
                .path("sellers")
                .entity(SellerPageableResponse.class)
                .get();

        assertThat(response).isNotNull();
        assertThat(response.data().size()).isEqualTo(2);
        assertThat(response.data().get(0).sellerName()).isEqualTo("Sarah");
        assertThat(response.data().get(0).externalId()).isEqualTo("ext_id_2");
        assertThat(response.data().get(0).producerSellerStates().size()).isEqualTo(1);
        assertThat(response.data().get(0).producerSellerStates().get(0).sellerState())
                .isEqualTo(SellerState.BLACKLISTED);
        assertThat(response.data().get(1).sellerName()).isEqualTo("John");
        assertThat(response.meta().currentPage()).isEqualTo(0);
        assertThat(response.meta().pageSize()).isEqualTo(2);
    }

    @Test
    void shouldReturnSellerDetails_WithCorrectPagination() {
        var response = httpGraphQlTester
                .documentName("sellerDetails")
                .variable("filter", null)
                .variable("sortBy", "NAME_DESC")
                .variable("page", Map.of("page", 0, "size", 1))
                .execute()
                .errors()
                .verify()
                .path("sellers")
                .entity(SellerPageableResponse.class)
                .get();

        assertThat(response).isNotNull();
        assertThat(response.data().size()).isEqualTo(1);
        assertThat(response.meta().currentPage()).isEqualTo(0);
        assertThat(response.meta().pageSize()).isEqualTo(1);
    }

    @Test
    void shouldReturnSellerDetails_FilteredByName() {
        var response = httpGraphQlTester
                .documentName("sellerDetails")
                .variable("filter", Map.of("searchByName", "John"))
                .variable("sortBy", "NAME_DESC")
                .variable("page", Map.of("page", 0, "size", 2))
                .execute()
                .errors()
                .verify()
                .path("sellers")
                .entity(SellerPageableResponse.class)
                .get();

        assertThat(response).isNotNull();
        assertThat(response.data().size()).isEqualTo(1);
        assertThat(response.data().get(0).sellerName()).isEqualTo("John");
    }

    @Test
    void shouldReturnSellerDetails_FilteredByMarketPlaceIds() {
        var response = httpGraphQlTester
                .documentName("sellerDetails")
                .variable("filter", Map.of("marketplaceIds", List.of(marketplaceIds.get(0))))
                .variable("sortBy", "NAME_DESC")
                .variable("page", Map.of("page", 0, "size", 2))
                .execute()
                .errors()
                .verify()
                .path("sellers")
                .entity(SellerPageableResponse.class)
                .get();

        assertThat(response).isNotNull();
        assertThat(response.data().size()).isEqualTo(1);
        assertThat(response.data().get(0).marketplaceId()).isEqualTo(marketplaceIds.get(0));
    }

    @Test
    void shouldReturnSellerDetails_FilteredByProducerIds() {
        var response = httpGraphQlTester
                .documentName("sellerDetails")
                .variable("filter", Map.of("producerIds", List.of(producerIds.get(0))))
                .variable("sortBy", "NAME_DESC")
                .variable("page", Map.of("page", 0, "size", 2))
                .execute()
                .errors()
                .verify()
                .path("sellers")
                .entity(SellerPageableResponse.class)
                .get();

        assertThat(response).isNotNull();
        assertThat(response.data().size()).isEqualTo(1);
        assertThat(response.data().get(0).producerSellerStates().get(0).producerId())
                .isEqualTo(producerIds.get(0));
    }

    @Test
    void shouldReturnSellerDetails_FilteredByAllFilters() {
        var response = httpGraphQlTester
                .documentName("sellerDetails")
                .variable(
                        "filter",
                        Map.of(
                                "searchByName", "John",
                                "marketplaceIds", List.of(marketplaceIds.get(0)),
                                "producerIds", List.of(producerIds.get(1))))
                .variable("sortBy", "NAME_DESC")
                .variable("page", Map.of("page", 0, "size", 2))
                .execute()
                .errors()
                .verify()
                .path("sellers")
                .entity(SellerPageableResponse.class)
                .get();

        assertThat(response).isNotNull();
        assertThat(response.data().size()).isEqualTo(2);
    }
}
