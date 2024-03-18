package com.sentryc.seller.repository;

import com.sentryc.seller.model.Producer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, UUID> {}
