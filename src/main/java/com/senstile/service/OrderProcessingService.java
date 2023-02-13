package com.senstile.service;

import com.senstile.persistance.*;
import com.senstile.utilities.ProviderDeliveryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Component
@Service
public class OrderProcessingService {

    @Autowired
    private ProviderService providerService;
    @Autowired
    private ProviderRepository providerRepository;

    public String sendToProcessing(DeliveryOrder deliveryOrder, Address address) throws ProviderDeliveryException {
        Provider provider = detectProvider();
        if(provider == null ){
            throw new ProviderDeliveryException();
        }
        deliveryOrder.setProviderName(provider.getName());
        String providerOrderId = providerService.process(address, deliveryOrder.getProductIds());
        return providerOrderId;
    }

    private Provider detectProvider() {
        int providerId = new Random().nextInt(2) + 1;
        Optional<Provider> provider = providerRepository.findById((long) providerId);
        if(provider.isPresent()) {
            return provider.get();
        }
        return null;
    }
}
