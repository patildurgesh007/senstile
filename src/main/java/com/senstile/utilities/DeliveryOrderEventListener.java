package com.senstile.utilities;

import com.senstile.persistance.DeliveryOrder;
import com.senstile.persistance.DeliveryOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public class DeliveryOrderEventListener{

    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;

//    @EventListener
//    public void onApplicationEvent(DeliveryOrderEvent event) {
//        DeliveryOrder deliveryOrder = event.getDeliveryOrder();
//        deliveryOrderRepository.save(deliveryOrder);
//    }
}
