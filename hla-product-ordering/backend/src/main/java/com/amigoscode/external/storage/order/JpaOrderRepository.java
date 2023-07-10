package com.amigoscode.external.storage.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderEntity,Integer> {

}
