package com.senstile.persistance;

import com.senstile.orders.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
    List<DeliveryOrder> findAllByExecuteAtBefore(Instant date);
    List<DeliveryOrder> findAllByStatusNotInAndExecuteAtBefore(List<OrderStatus> orderStatusList,Instant date);
}
