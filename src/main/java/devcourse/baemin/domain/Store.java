package devcourse.baemin.domain;

import devcourse.baemin.domain.dto.StoreViewDto;
import devcourse.baemin.domain.value.Amount;

public class Store {

    private final String storeName;
    private final Amount minimumOrderAmount;

    public Store(String storeName, Amount minimumOrderAmount) {
        this.storeName = storeName;
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public long getMinimumOrderAmount() {
        return this.minimumOrderAmount.getAmount();
    }

    public StoreViewDto toViewDto() {
        return new StoreViewDto(this.storeName, getMinimumOrderAmount());
    }
}
