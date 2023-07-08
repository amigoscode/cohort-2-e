package com.amigoscode.domain.provider;

import com.amigoscode.BaseIT;
import com.amigoscode.TestProviderFactory;
import com.amigoscode.TestUserFactory;
import com.amigoscode.domain.user.User;
import com.amigoscode.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProviderServiceIT extends BaseIT {

    @Autowired
    ProviderService service;


    @Test
    void add_provider_test() {
        //given
        Provider provider = TestProviderFactory.create();
        Provider savedProvider = service.save(provider);

        //when
        Provider readProvider = service.findById(savedProvider.getId());

        //then
        Assertions.assertEquals(provider.getEmail(), readProvider.getEmail());
        Assertions.assertEquals(provider.getName(), readProvider.getName());
    }

    @Test
    void get_by_id_should_return_correct_provider() {
        //given
        Provider provider1 = TestProviderFactory.create();
        Provider provider2 = TestProviderFactory.create();
        Provider provider3 = TestProviderFactory.create();
        Provider savedProvider1 = service.save(provider1);
        Provider savedProvider2 = service.save(provider2);
        Provider savedProvider3 = service.save(provider3);

        //when
        Provider readProvider = service.findById(savedProvider2.getId());

        //then
        Assertions.assertEquals(provider2.getEmail(), readProvider.getEmail());
        Assertions.assertEquals(provider2.getName(), readProvider.getName());
    }
}