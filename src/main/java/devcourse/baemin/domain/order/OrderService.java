package devcourse.baemin.domain.order;

import devcourse.baemin.domain.menu.MenuDto;
import devcourse.baemin.domain.menu.MenuService;
import devcourse.baemin.domain.order.model.Order;
import devcourse.baemin.domain.order.model.OrderCreationDto;
import devcourse.baemin.domain.order.model.OrderDetailDto;
import devcourse.baemin.domain.store.StoreService;
import devcourse.baemin.domain.value.Amount;
import devcourse.baemin.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class OrderService {

    private final MenuService menuService;
    private final StoreService storeService;

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, MenuService menuService, StoreService storeService) {
        this.orderRepository = orderRepository;
        this.menuService = menuService;
        this.storeService = storeService;
    }

    public OrderDetailDto createOrder(OrderCreationDto orderCreationDto) {
        Order newOrder = toOrder(orderCreationDto);
        orderRepository.save(newOrder);
        return getOrderByIdAsDto(newOrder.getOrderId());
    }

    public OrderDetailDto getOrderByIdAsDto(UUID orderId) {
        return toOrderDetailDto(findOrderById(orderId));
    }

    private Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("No order exits for orderId={0}", orderId)
                ));
    }

    private Order toOrder(OrderCreationDto orderCreationDto) {
        MenuDto.ResponseDto menuResponseDto = menuService.getMenuAsDto(orderCreationDto.getMenuId());

        return new Order(
                UUID.randomUUID(),
                orderCreationDto.getMemberId(),
                orderCreationDto.getMenuId(),
                menuResponseDto.getStoreId(),
                orderCreationDto.getOrderQuantity(),
                new Amount(menuResponseDto.getPrice() * orderCreationDto.getOrderQuantity()),
                OrderStatus.PAID,
                TimeUtil.getCurrentSeoulTime()
        );
    }

    private OrderDetailDto toOrderDetailDto(Order order) {
        return new OrderDetailDto(
                order,
                menuService.findMenuById(order.getMenuId()),
                storeService.findStoreById(order.getStoreId())
        );
    }
}
