package devcourse.baemin.domain.order.model;

import devcourse.baemin.domain.menu.Menu;
import devcourse.baemin.domain.order.OrderStatus;
import devcourse.baemin.domain.store.Store;
import devcourse.baemin.domain.value.Amount;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderDetailDto {

    private final UUID orderId;
    private final String memberId;
    private final long orderQuantity;
    private final Amount orderAmount;
    private final LocalDateTime createdAt;
    private String menuName;
    private String storeName;
    private OrderStatus orderStatus;
    private LocalDateTime cancelledAt;

    public OrderDetailDto(Order order, Menu menu, Store store) {
        this.orderId = order.getOrderId();
        this.memberId = order.getMemberId();
        this.menuName = menu.getMenuName();
        this.storeName = store.getStoreName();
        this.orderQuantity = order.getOrderQuantity();
        this.orderAmount = new Amount(order.getOrderAmount());
        this.createdAt = order.getCreatedAt();
        this.orderStatus = OrderStatus.valueOf(order.getOrderStatus());
        this.cancelledAt = order.getCancelledAt();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getStoreName() {
        return storeName;
    }

    public long getOrderQuantity() {
        return orderQuantity;
    }

    public long getOrderAmount() {
        return orderAmount.getAmount();
    }

    public String getOrderStatus() {
        return orderStatus.toString();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCancelledAt() {
        if (this.cancelledAt == null) {
            return "Not cancelled yet.";
        }
        return this.cancelledAt.toString();
    }
}
