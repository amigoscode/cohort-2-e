package com.amigoscode.api.order;


import com.amigoscode.api.provider.ProviderDto;
import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/orders",
        produces = "application/json",
        consumes = "application/json"
)
class OrderController {
    private final OrderService orderService;

    private final OrderDtoMapper orderMapper;

    @GetMapping( path = "/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Integer orderId) {
        Order order = orderService.findById(orderId);
        return ResponseEntity
                .ok(orderMapper.toDto(order));
    }
}
