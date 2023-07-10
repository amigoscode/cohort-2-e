package com.amigoscode.api.order;

import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.PageOrder;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageOrderDtoMapper {

    @Mapping(target = "orders", qualifiedByName = "toOrderDtoList")
    PageOrderDto toPageDto(PageOrder domain);

    @Named("toOrderDtoList")
    @IterableMapping(qualifiedByName = "orderToOrderDto")
    List<OrderDto> toListDto(List<Order> orders);

    @Named("orderToOrderDto")
    OrderDto toDto(Order domain);
}