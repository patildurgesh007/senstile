package com.senstile.controller;

import com.senstile.persistance.Provider;
import com.senstile.persistance.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;


@RestController
@EnableSwagger2
@RequestMapping("/api")
public class ProviderController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/allProviders")
    public List<Provider> findAll() {
        return context.getBean(ProviderRepository.class).findAll();
    }
}
