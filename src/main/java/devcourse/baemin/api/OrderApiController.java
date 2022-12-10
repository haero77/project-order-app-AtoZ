package devcourse.baemin.api;

import devcourse.baemin.api.response.CommonResponse;
import devcourse.baemin.domain.order.OrderService;
import devcourse.baemin.domain.order.model.OrderCreationDto;
import devcourse.baemin.domain.order.model.OrderDetailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderApiController {

    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponse<List<OrderDetailDto>>> getOrders(
            @PathVariable String memberId
    ) {
        List<OrderDetailDto> orderDetailDtos = orderService.getOrdersByMemberIdAsDto(memberId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        MessageFormat.format("Found all order history for member ''{0}''", memberId),
                        orderDetailDtos
                ));
    }

    @GetMapping("/member/{orderId}")
    public ResponseEntity<CommonResponse<OrderDetailDto>> getOrder(
            @PathVariable UUID orderId
    ) {
        OrderDetailDto orderDetailDto = orderService.getOrderByIdAsDto(orderId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        MessageFormat.format("Found order for orderId ''{0}''", orderId),
                        orderDetailDto)
                );
    }

    @PostMapping
    public ResponseEntity<CommonResponse<OrderDetailDto>> createOrder(
            @RequestBody OrderCreationDto orderCreationDto
    ) {
        OrderDetailDto orderDetailDto = orderService.createOrder(orderCreationDto);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Order created.", orderDetailDto));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<CommonResponse<OrderDetailDto>> cancelOrder(
            @PathVariable UUID orderId
    ) {
        OrderDetailDto orderDetailDto = orderService.cancelOrder(orderId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        MessageFormat.format("Order ''{0}'' has been cancelled.", orderId),
                        orderDetailDto
                ));
    }
}
