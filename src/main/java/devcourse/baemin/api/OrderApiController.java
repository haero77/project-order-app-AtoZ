package devcourse.baemin.api;

import devcourse.baemin.api.response.CommonResponse;
import devcourse.baemin.domain.order.OrderService;
import devcourse.baemin.domain.order.model.OrderCreationDto;
import devcourse.baemin.domain.order.model.OrderDetailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderApiController {

    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<OrderDetailDto>> createOrder(
            @RequestBody OrderCreationDto orderCreationDto
    ) {
        OrderDetailDto orderDetailDto = orderService.createOrder(orderCreationDto);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Order created.", orderDetailDto));
    }

    // TODO: GET /orders/{memberId}


    // TODO: GET /orders/{orderId}

    // TODO: PATCH /orders/{orderId}/cancel
}
