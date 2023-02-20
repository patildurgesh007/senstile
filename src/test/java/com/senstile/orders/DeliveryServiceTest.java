package com.senstile.orders;

import com.senstile.controller.AddressController;
import com.senstile.persistance.AddressRepository;
import com.senstile.service.AddressService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebAppConfiguration
public class DeliveryServiceTest {

    private MockMvc mockMvc;

    @Autowired
    AddressController addressController;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    public void setup() {
        addressController = new AddressController();
        addressService = new AddressService();
        mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .build();
    }

    @Test
    public void allAddressesNotFoundTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/allAddresses").contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void allAddressesTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/allAddresses").contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }


}
