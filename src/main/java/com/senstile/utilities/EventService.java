package com.senstile.utilities;

import com.senstile.orders.OrderStatus;
import com.senstile.persistance.DeliveryOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EventService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Async
    public void created(DeliveryOrder deliveryOrder) {

    }

    @Async
    public void sentToProvider(DeliveryOrder deliveryOrder) {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int randomNumber = new Random().nextInt(10);
        if(randomNumber<8) {
            deliveryOrder.setStatus(OrderStatus.COMPLETE);
        }else {
            deliveryOrder.setStatus(OrderStatus.FAILED);
        }
        publisher.publishEvent(new DeliveryOrderEvent(deliveryOrder));
    }

    @Async
    public void failedToSendToProvider(DeliveryOrder deliveryOrder) {
        deliveryOrder.setStatus(OrderStatus.FAILED);
        publisher.publishEvent(new DeliveryOrderEvent(deliveryOrder));
    }

}
