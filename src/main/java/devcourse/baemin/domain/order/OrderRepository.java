package devcourse.baemin.domain.order;

import devcourse.baemin.domain.order.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void save(Order order);

    List<Order> findAll();

    Optional<Order> findById(UUID orderId);

    void updateCancelledAt(UUID orderId, LocalDateTime cancelledAt);
}
