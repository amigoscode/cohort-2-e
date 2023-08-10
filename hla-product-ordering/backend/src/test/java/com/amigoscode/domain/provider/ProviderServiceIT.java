package com.amigoscode.domain.provider;

import com.amigoscode.BaseIT;
import com.amigoscode.TestProviderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProviderServiceIT extends BaseIT {

    @Autowired
    ProviderService service;

    @Autowired
    UserService userService;

    @Test
    void add_provider_test() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);


        Provider provider = TestProviderFactory.create();
        provider.setCreatedBy(savedUser.getId());
        Provider savedProvider = service.save(provider, savedUser.getId());

        //when
        Provider readProvider = service.findById(savedProvider.getId());

        //then
        Assertions.assertEquals(provider.getEmail(), readProvider.getEmail());
        Assertions.assertEquals(provider.getName(), readProvider.getName());
    }

    @Test
    void get_by_id_should_return_correct_provider() {
        //given
        User user = TestUserFactory.createTechnologist();
        User savedUser = userService.save(user);

        Provider provider1 = TestProviderFactory.create();
        Provider provider2 = TestProviderFactory.create();
        Provider provider3 = TestProviderFactory.create();


        provider1.setCreatedBy(savedUser.getId());
        provider2.setCreatedBy(savedUser.getId());
        provider3.setCreatedBy(savedUser.getId());

        Provider savedProvider1 = service.save(provider1, savedUser.getId());
        Provider savedProvider2 = service.save(provider2, savedUser.getId());
        Provider savedProvider3 = service.save(provider3, savedUser.getId());

        //when
        Provider readProvider = service.findById(savedProvider2.getId());

        //then
        Assertions.assertEquals(provider2.getEmail(), readProvider.getEmail());
        Assertions.assertEquals(provider2.getName(), readProvider.getName());
    }
}