package com.amigoscode.config;

import com.amigoscode.domain.order.OrderRepository;
import com.amigoscode.domain.order.OrderService;
import com.amigoscode.domain.provider.ProviderRepository;
import com.amigoscode.domain.provider.ProviderService;
import com.amigoscode.domain.schedule.ScheduleRepository;
import com.amigoscode.domain.schedule.ScheduleService;
import com.amigoscode.domain.user.EncodingService;
import com.amigoscode.domain.user.UserRepository;
import com.amigoscode.domain.user.UserService;
import com.amigoscode.external.storage.order.JpaOrderRepository;
import com.amigoscode.external.storage.order.OrderEntityMapper;
import com.amigoscode.external.storage.order.OrderStorageAdapter;
import com.amigoscode.external.storage.provider.JpaProviderRepository;
import com.amigoscode.external.storage.provider.ProviderEntityMapper;
import com.amigoscode.external.storage.provider.ProviderStorageAdapter;
import com.amigoscode.external.storage.schedule.JpaScheduleRepository;
import com.amigoscode.external.storage.schedule.ScheduleEntityMapper;
import com.amigoscode.external.storage.schedule.ScheduleStorageAdapter;
import com.amigoscode.external.storage.user.JpaUserRepository;
import com.amigoscode.external.storage.user.UserEntityMapper;
import com.amigoscode.external.storage.user.UserStorageAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository, UserEntityMapper mapper) {
        return new UserStorageAdapter(jpaUserRepository, mapper);
    }

    @Bean
    public UserService userService(UserRepository userRepository, EncodingService encoder, Clock clock)  {
        return new UserService(userRepository, encoder, clock);
    }

    @Bean
    public ProviderRepository providerRepository(JpaProviderRepository jpaProviderRepository, ProviderEntityMapper mapper) {
        return new ProviderStorageAdapter(jpaProviderRepository, mapper);
    }

    @Bean
    public ProviderService providerService(ProviderRepository providerRepository, Clock clock)  {
        return new ProviderService(providerRepository, clock);
    }

    @Bean
    public OrderRepository orderRepository(JpaOrderRepository jpaOrderRepository, OrderEntityMapper mapper){
        return new OrderStorageAdapter(jpaOrderRepository, mapper);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository){
        return new OrderService(orderRepository);
    }

    @Bean
    public ScheduleRepository scheduleRepository(JpaScheduleRepository jpaScheduleRepository, ScheduleEntityMapper mapper){
        return new ScheduleStorageAdapter(jpaScheduleRepository, mapper);
    }

    @Bean
    public ScheduleService scheduleRepository(ScheduleRepository scheduleRepository){
        return new ScheduleService(scheduleRepository);
    }

}
