package com.amigoscode.domain.order;

import lombok.RequiredArgsConstructor;

import java.awt.print.Pageable;

@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    public Order findById(Integer id){
        return orderRepository.findById(id).
                orElseThrow(OrderNotFoundException::new);
    }

    public PageOrder findAll(Pageable pageable) { return orderRepository.findAll(pageable); }
}
