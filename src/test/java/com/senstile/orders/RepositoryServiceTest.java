package com.senstile.orders;

import com.senstile.persistance.*;
import com.senstile.service.DeliveryOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepositoryServiceTest {

    @InjectMocks
    DeliveryOrderService deliveryOrderService;

    @Mock
    DeliveryOrderRepository deliveryOrderRepository ;

    @Mock
    AddressRepository addressRepository ;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getDeliveryOrderByIdTest()
    {
        when(deliveryOrderRepository.findById(new Long(1))).thenReturn(Optional.of(new DeliveryOrder(1L, 1L, new ArrayList<>(), Instant.now())));

        Optional<DeliveryOrder> deliveryOrderOptional = deliveryOrderService.findById(1L);
        Assertions.assertNotNull(deliveryOrderOptional.get());
        DeliveryOrder deliveryOrder = deliveryOrderOptional.get();
        Assertions.assertEquals(new Long(1), deliveryOrder.getAddressId());
        Assertions.assertEquals(new Long(1), deliveryOrder.getUserId());
    }

    @Test
    public void getAddressByIdTest()
    {
        when(addressRepository.findById(new Long(100))).thenReturn(Optional.of(new Address(new User(100L, "TESTUSER"),"some street 1", "New York", "US", "1234")));
        Optional<Address> addressOptional = addressRepository.findById(100L);
        Assertions.assertNotNull(addressOptional.get());
        Address address = addressOptional.get();
        Assertions.assertEquals("New York", address.getCity());
        Assertions.assertEquals("1234", address.getPostalCode());
    }
}
