package com.amigoscode.domain.order;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    public Order findById(Integer id){
        return orderRepository.findById(id).
                orElseThrow(OrderNotFoundException::new);
    }

    public PageOrder findAll(Pageable pageable) { return orderRepository.findAll(pageable); }
}
