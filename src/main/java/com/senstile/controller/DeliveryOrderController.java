package com.senstile.controller;

import com.senstile.service.AddressService;
import com.senstile.service.UserService;
import com.senstile.utilities.Constants;
import com.senstile.utilities.Utils;
import com.senstile.persistance.*;
import com.senstile.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;


@RestController
@EnableSwagger2
@RequestMapping("/api")
public class DeliveryOrderController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @PostMapping("/processDeliveryOrder/{isImmediateProcess}")
    public ResponseEntity processDeliveryOrder(@RequestBody DeliveryOrder deliveryOrder, @PathVariable("isImmediateProcess") boolean isImmediateProcess) {

        if(Utils.isAnyNullOrEmpty(deliveryOrder.getUserId(),deliveryOrder.getAddressId(),deliveryOrder.getExecuteAt(),deliveryOrder.getProductIds())){
            return new ResponseEntity(Constants.MSG_PARAM_MISSING, HttpStatus.BAD_REQUEST);
        }
        try {
            userService.findById(deliveryOrder.getUserId());
        } catch (Exception e) {
            return new ResponseEntity(Constants.MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        if (!context.getBean(AddressRepository.class).findById(deliveryOrder.getAddressId()).isPresent()) {
            return new ResponseEntity(Constants.MSG_PAYMENT_METHOD_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        DeliveryOrder deliveryOrderNew = deliveryOrderService.saveDeliveryOrder(deliveryOrder,isImmediateProcess);
        return new ResponseEntity(deliveryOrderNew, HttpStatus.OK);
    }

    @GetMapping("/allDeliveryOrders")
    public ResponseEntity findAll() {
        List<DeliveryOrder> deliveryOrders = deliveryOrderService.findAll();
        return new ResponseEntity(deliveryOrders, HttpStatus.OK);
    }

    @GetMapping("/deliveryOrder/{id}")
    public ResponseEntity findById(Long id) {
        Optional<DeliveryOrder> deliveryOrder =  deliveryOrderService.findById(id);
        if(deliveryOrder.isPresent()) {
            return new ResponseEntity(deliveryOrder, HttpStatus.OK);
        }
        return new ResponseEntity(Constants.MSG_USER_NOT_FOUND, HttpStatus.NOT_FOUND);

    }

}
