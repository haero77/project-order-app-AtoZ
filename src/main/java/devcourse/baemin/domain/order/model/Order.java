package devcourse.baemin.domain.order.model;

import devcourse.baemin.domain.order.OrderStatus;
import devcourse.baemin.global.value.Amount;
import devcourse.baemin.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final String memberId;
    private final UUID menuId;
    private final UUID storeId;
    private final long orderQuantity;
    private final Amount orderAmount;
    private final LocalDateTime createdAt;
    private OrderStatus orderStatus;
    private LocalDateTime cancelledAt;

    public Order(UUID orderId, String memberId, UUID menuId, UUID storeId, long orderQuantity,
                 Amount orderAmount, OrderStatus orderStatus, LocalDateTime createdAt
    ) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.menuId = menuId;
        this.storeId = storeId;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public Order(UUID orderId, String memberId, UUID menuId, UUID storeId, long orderQuantity, Amount orderAmount,
                 OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime cancelledAt
    ) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.menuId = menuId;
        this.storeId = storeId;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.cancelledAt = cancelledAt;
    }

    public void cancel() {
        this.cancelledAt = TimeUtil.getCurrentSeoulTime();
    }

    public boolean isOrderedByMember(String memberId) {
        return this.getMemberId().equals(memberId);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public UUID getStoreId() {
        return storeId;
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

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }
}
