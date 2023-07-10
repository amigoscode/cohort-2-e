package com.amigoscode.domain.order;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    public Order findById(Integer id){
        return orderRepository.findById(id).
                orElseThrow(OrderNotFoundException::new);
    }

    public PageOrder findAll(Pageable pageable) { return orderRepository.findAll(pageable); }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void update(Order order){
        orderRepository.update(order);
    }

    public void removeById(Integer id){
        orderRepository.removeById(id);
    }

}
