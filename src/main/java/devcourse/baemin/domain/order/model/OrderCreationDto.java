package devcourse.baemin.domain.order.model;

import java.util.UUID;

public class OrderCreationDto {

    private String memberId;
    private UUID menuId;
    private long orderQuantity;

    public OrderCreationDto() {
    }

    public OrderCreationDto(String memberId, UUID menuId, long orderQuantity) {
        this.memberId = memberId;
        this.menuId = menuId;
        this.orderQuantity = orderQuantity;
    }

    public String getMemberId() {
        return memberId;
    }

    public UUID getMenuId() {
        return this.menuId;
    }

    public long getOrderQuantity() {
        return this.orderQuantity;
    }
}
