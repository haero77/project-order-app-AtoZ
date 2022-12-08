package devcourse.baemin.domain.store;

import devcourse.baemin.domain.value.Amount;

import java.util.UUID;

public class Store {

    private final UUID storeId;
    private final String storeName;
    private final Amount minimumOrderAmount;

    public Store(UUID storeId, String storeName, Amount minimumOrderAmount) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public long getMinimumOrderAmount() {
        return this.minimumOrderAmount.getAmount();
    }
}
