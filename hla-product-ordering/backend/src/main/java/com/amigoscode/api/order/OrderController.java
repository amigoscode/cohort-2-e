package com.amigoscode.api.order;


import com.amigoscode.api.provider.PageProviderDto;
import com.amigoscode.api.provider.ProviderDto;
import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/orders",
        produces = "application/json",
        consumes = "application/json"
)
class OrderController {
    private final OrderService orderService;

    private final OrderDtoMapper orderMapper;

    private final PageOrderDtoMapper pageOrderDtoMapper;

    @GetMapping( path = "/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Integer orderId) {
        Order order = orderService.findById(orderId);
        return ResponseEntity
                .ok(orderMapper.toDto(order));
    }

    @GetMapping
    public ResponseEntity<PageOrderDto> getProviders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageOrderDto pageOrders = pageOrderDtoMapper.toPageDto(orderService.findAll(pageable));

        return ResponseEntity.ok(pageOrders);
    }

}
