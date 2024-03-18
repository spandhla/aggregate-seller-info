package com.sentryc.seller.repository;

import com.sentryc.seller.dto.PageInput;
import com.sentryc.seller.dto.SellerFilter;
import com.sentryc.seller.dto.SellerSortBy;
import com.sentryc.seller.model.MarketPlace;
import com.sentryc.seller.model.Producer;
import com.sentryc.seller.model.Seller;
import com.sentryc.seller.model.SellerInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellerInfoSearchDao {

    private final EntityManager em;

    public List<SellerInfo> findAll(SellerFilter filter, PageInput page, SellerSortBy sortBy) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<SellerInfo> criteriaQuery = criteriaBuilder.createQuery(SellerInfo.class);
        Root<SellerInfo> root = criteriaQuery.from(SellerInfo.class);

        Join<SellerInfo, MarketPlace> marketPlaceJoin = root.join("marketplace");
        Join<SellerInfo, Seller> sellerJoin = root.join("seller");
        Join<Seller, Producer> producerJoin = sellerJoin.join("producer");

        sort(criteriaBuilder, criteriaQuery, root, marketPlaceJoin, sortBy);

        var predicates = filter(criteriaBuilder, filter, root, marketPlaceJoin, producerJoin);
        if (!predicates.isEmpty()) {
            Predicate andPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            criteriaQuery.where(andPredicate);
        }

        TypedQuery<SellerInfo> query = em.createQuery(criteriaQuery);
        var pagedData = paginateQuery(query, page).getResultList();

        return pagedData;
    }

    private <T> TypedQuery<T> paginateQuery(TypedQuery<T> query, PageInput page) {
        query.setFirstResult(page.page());
        query.setMaxResults(page.size());
        return query;
    }

    private List<Predicate> filter(
            CriteriaBuilder criteriaBuilder,
            SellerFilter filter,
            Root<SellerInfo> root,
            Join<SellerInfo, MarketPlace> marketPlaceJoin,
            Join<Seller, Producer> producerJoin) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter != null) {
            if (filter.searchByName() != null) {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + filter.searchByName() + "%");
                predicates.add(namePredicate);
            }

            if (filter.producerIds() != null && !filter.producerIds().isEmpty()) {
                var producerIdsPredicate = criteriaBuilder.in(producerJoin.get("id"));
                for (UUID id : filter.producerIds()) {
                    producerIdsPredicate.value(id);
                }
                predicates.add(producerIdsPredicate);
            }

            if (filter.marketplaceIds() != null && !filter.marketplaceIds().isEmpty()) {
                var marketplaceIdsPredicate = criteriaBuilder.in(marketPlaceJoin.get("id"));
                for (String id : filter.marketplaceIds()) {
                    marketplaceIdsPredicate.value(id);
                }
                predicates.add(marketplaceIdsPredicate);
            }
        }

        return predicates;
    }

    private void sort(
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<SellerInfo> criteriaQuery,
            Root<SellerInfo> sellerInfo,
            Join<SellerInfo, MarketPlace> marketPlace,
            SellerSortBy sortBy) {
        if (sortBy.name().startsWith("MARKETPLACE_ID")) {
            sortMarketPlace(criteriaBuilder, criteriaQuery, marketPlace, sortBy);
        } else {
            sortSellerInfo(criteriaBuilder, criteriaQuery, sellerInfo, sortBy);
        }
    }

    private void sortSellerInfo(
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<SellerInfo> criteriaQuery,
            Root<SellerInfo> sellerInfo,
            SellerSortBy sortBy) {
        String sortProperty = sortBy.getSortBy().getFirst();
        if (sortBy.getSortBy().getSecond().isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(sellerInfo.get(sortProperty)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(sellerInfo.get(sortProperty)));
        }
    }

    private void sortMarketPlace(
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<SellerInfo> criteriaQuery,
            Join<SellerInfo, MarketPlace> marketPlace,
            SellerSortBy sortBy) {
        String sortProperty = sortBy.getSortBy().getFirst();
        if (sortBy.getSortBy().getSecond().isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(marketPlace.get(sortProperty)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(marketPlace.get(sortProperty)));
        }
    }
}
