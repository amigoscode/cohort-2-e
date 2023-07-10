package com.amigoscode.domain.order;

import com.amigoscode.domain.provider.PageProvider;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Integer id);

    PageOrder findAll(Pageable pageable);
}
