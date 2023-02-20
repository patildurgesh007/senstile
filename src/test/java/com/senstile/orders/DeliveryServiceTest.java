package com.senstile.orders;


import com.senstile.persistance.DeliveryOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Nested
class DeliveryServiceTest  extends AbstractTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }


    @Test
    @Sql(scripts = "classpath:importTest.sql")
    public void processOrderTest() throws Exception {
        String uri = "http://localhost:7070/api/processDeliveryOrder/true";
        List<Long> products = new ArrayList<Long>();
        products.add(1L);
        DeliveryOrder deliveryOrder = new DeliveryOrder(1L,10L,products, Instant.now());
        String inputJson = super.mapToJson(deliveryOrder);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


}
