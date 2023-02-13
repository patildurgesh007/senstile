package com.senstile.service;

import com.senstile.persistance.Address;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProviderService {

    public String process(Address address, List<Long> productIds) {
        return UUID.randomUUID().toString();
    }
}
