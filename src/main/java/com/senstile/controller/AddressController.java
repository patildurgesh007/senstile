package com.senstile.controller;

import com.senstile.persistance.Address;
import com.senstile.persistance.AddressRepository;
import com.senstile.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("/api")
public class AddressController {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private AddressService addressService;

    @GetMapping("/allAddresses")
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/address/{id}")
    public Address findAddress(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @PostMapping("/address")
    public ResponseEntity addUser(@RequestBody Address address){
        addressService.save(address);
        return new ResponseEntity(address, HttpStatus.OK);
    }
}
