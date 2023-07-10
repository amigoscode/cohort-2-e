package com.amigoscode.external.storage.order;

import com.amigoscode.domain.order.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    Order toDomain(OrderEntity entity);

    OrderEntity toEntity(Order domain);
}
