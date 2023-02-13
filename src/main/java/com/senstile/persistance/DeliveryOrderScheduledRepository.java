package com.senstile.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DeliveryOrderScheduledRepository extends JpaRepository<DeliveryOrderScheduled, Long> {

    List<DeliveryOrderScheduled> findAllByExecuteAtBefore(Instant date);
}
