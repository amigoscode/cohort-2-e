package com.amigoscode.api.order;

import com.amigoscode.domain.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    OrderDto toDto(Order domain);

}
