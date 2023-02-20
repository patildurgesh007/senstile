package com.senstile.orders;


import antlr.build.Tool;
import com.senstile.controller.AddressController;
import com.senstile.persistance.Address;
import com.senstile.persistance.AddressRepository;
import com.senstile.persistance.User;
import com.senstile.service.AddressService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveAddressTest(){
        Address address = new Address(new User(1L,"TestUser"),"some street 1", "New York", "US", "1234");
        addressRepository.save(address);
        Assertions.assertThat(address.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getAddressTest(){
        Address address = addressRepository.findById(1L).get();
        Assertions.assertThat(address.getId()).isEqualTo(1L);
    }
}
