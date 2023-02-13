package com.senstile.utilities;

import com.senstile.orders.OrderStatus;
import com.senstile.persistance.DeliveryOrder;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderEvent {
    private DeliveryOrder deliveryOrder;

    public DeliveryOrderEvent(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }
}
