package devcourse.baemin.domain;

import devcourse.baemin.domain.value.Amount;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final UUID menuId;
    private final Amount orderAmount;
    private final long orderQuantity;
    private final String storeName;
    private final String customerName;
    private final OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime cancelledAt;

    public Order(UUID orderId, Menu menu, Amount orderAmount, long orderQuantity, Store store,
                 String customerName, OrderStatus orderStatus, LocalDateTime createdAt
    ) {
        this.orderId = orderId;
        this.menuId = menu.getMenuId();
        this.orderAmount = orderAmount;
        this.orderQuantity = orderQuantity;
        this.storeName = store.getStoreName();
        this.customerName = customerName;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public UUID getMenuId() {
        return this.menuId;
    }

    public long getOrderAmount() {
        return this.orderAmount.getAmount();
    }

    public long getOrderQuantity() {
        return orderQuantity;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCancelledAt() {
        if (this.cancelledAt == null) {
            throw new IllegalStateException(
                    MessageFormat.format("No cancel history exists for order ''{0}''", this.orderId)
            );
        }
        return this.cancelledAt;
    }
}
