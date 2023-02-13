package com.senstile.service;

import com.senstile.persistance.Address;
import com.senstile.persistance.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan
public class AddressService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private AddressRepository addressRepository;


    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void save(Address address) {
        addressRepository.save(address);
    }
}
