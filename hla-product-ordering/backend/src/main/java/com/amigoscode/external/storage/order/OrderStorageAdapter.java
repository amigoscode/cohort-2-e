package com.amigoscode.external.storage.order;

import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderRepository;
import com.amigoscode.domain.order.PageOrder;
import com.amigoscode.external.storage.provider.JpaProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class OrderStorageAdapter implements OrderRepository {
    private final JpaOrderRepository orderRepository;

    private final OrderEntityMapper mapper;


    @Override
    public Optional<Order> findById(final Integer id) {
        return orderRepository.findById(id).map(mapper::toDomain);
    }



    @Override
    public PageOrder findAll(Pageable pageable) {
        Page<OrderEntity> pageOfOrdersEntity = orderRepository.findAll(pageable);
        List<Order> ordersOnCurrentPage = pageOfOrdersEntity.getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
        return new PageOrder(
            ordersOnCurrentPage,
            pageable.getPageNumber() +1,
            pageOfOrdersEntity.getTotalPages(),
            pageOfOrdersEntity.getTotalElements()
        );
    }
}
