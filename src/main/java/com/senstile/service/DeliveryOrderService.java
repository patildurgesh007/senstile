package com.senstile.service;


import com.senstile.orders.OrderStatus;
import com.senstile.persistance.*;
import com.senstile.utilities.DeliveryOrderEvent;
import com.senstile.utilities.EventService;
import com.senstile.utilities.ProviderDeliveryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@ComponentScan
public class DeliveryOrderService {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;
    @Autowired
    private DeliveryOrderScheduledRepository deliveryOrderScheduledRepository;
    @Autowired
    private OrderProcessingService orderProcessingService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EventService eventService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void create(DeliveryOrder deliveryOrder) {
        DeliveryOrder pendingDeliveryOrder = deliveryOrderRepository.save(deliveryOrder);
        eventService.created(deliveryOrder);
        executorService.submit(() -> {
            Optional<DeliveryOrder> savedOrderOptional = deliveryOrderRepository.findById(pendingDeliveryOrder.getId());
            if (savedOrderOptional.isPresent()) {
                DeliveryOrder deliveryOrderUnderProcess = savedOrderOptional.get();
                processOrder(deliveryOrderUnderProcess);
            }
        });
    }

    public void schedule(DeliveryOrder deliveryOrder) {
        deliveryOrderRepository.save(deliveryOrder);
        eventService.created(deliveryOrder);
    }

    @Scheduled(fixedDelay = 5000)
    public void run() {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        orderStatusList.add(OrderStatus.COMPLETE);
        orderStatusList.add(OrderStatus.PROCESSING);
        deliveryOrderRepository.findAllByStatusNotInAndExecuteAtBefore(orderStatusList,(Instant.now()))
                .forEach(this::processOrder);
    }

    public void processOrder(DeliveryOrder deliveryOrder) {
        Address address = addressRepository.findById(deliveryOrder.getAddressId()).orElse(null);
        if (address != null) {
            try {
                String providerOrderId = orderProcessingService.sendToProcessing(deliveryOrder, address);
                deliveryOrder.setStatus(OrderStatus.PROCESSING);
                deliveryOrder.setProviderOrderId(providerOrderId);
                deliveryOrderRepository.save(deliveryOrder);
                eventService.sentToProvider(deliveryOrder);
            } catch (Exception e) {
                if (e instanceof ProviderDeliveryException) {
                    deliveryOrder.setStatus(OrderStatus.FAILED);
                    deliveryOrderRepository.save(deliveryOrder);
                    eventService.failedToSendToProvider(deliveryOrder);
                } else {
                    deliveryOrder.setStatus(OrderStatus.INTERNAL_ERROR);
                    deliveryOrderRepository.save(deliveryOrder);
                }
            }
        }
    }

    @Async
    @EventListener
    void handleDeliveryOrderEvent(DeliveryOrderEvent event){
        DeliveryOrder deliveryOrder = event.getDeliveryOrder();
        deliveryOrderRepository.save(deliveryOrder);
    }

    public DeliveryOrder saveDeliveryOrder(DeliveryOrder deliveryOrder, boolean isImmediateProcess) {
        DeliveryOrderService deliveryOrderService = context.getBean(DeliveryOrderService.class);
        deliveryOrder.setCreatedAt(Instant.now());
        deliveryOrder.setStatus(OrderStatus.PENDING);
        if (isImmediateProcess) {
            deliveryOrderService.create(deliveryOrder);
        } else {
            deliveryOrderService.schedule(deliveryOrder);
        }
        return deliveryOrder;
    }

    public List<DeliveryOrder> findAll() {
        return context.getBean(DeliveryOrderRepository.class).findAll();
    }

    public Optional<DeliveryOrder> findById(Long id) {
        return deliveryOrderRepository.findById(id);

    }
}
