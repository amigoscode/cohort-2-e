package com.amigoscode.external.storage.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOrderRepository extends JpaRepository<OrderEntity,Integer> {

    List<OrderEntity> findOrderEntitiesByEmailId(Integer emailId);

}
