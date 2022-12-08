package devcourse.baemin.domain.store;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class StoreDto {

    public static class RequestDto {

        @NotEmpty
        private String storeName;
        @NotEmpty
        private long minimumOrderAmount;

        public RequestDto() {
        }

        public RequestDto(String storeName, long minimumOrderAmount) {
            this.storeName = storeName;
            this.minimumOrderAmount = minimumOrderAmount;
        }

        public String getStoreName() {
            return this.storeName;
        }

        public long getMinimumOrderAmount() {
            return this.minimumOrderAmount;
        }
    }

    public static class ResponseDto {

        private final UUID storeId;
        private final String storeName;
        private final long minimumOrderAmount;

        public ResponseDto(Store store) {
            this.storeId = store.getStoreId();
            this.storeName = store.getStoreName();
            this.minimumOrderAmount = store.getMinimumOrderAmount();
        }

        public UUID getStoreId() {
            return this.storeId;
        }

        public String getStoreName() {
            return this.storeName;
        }

        public long getMinimumOrderAmount() {
            return this.minimumOrderAmount;
        }
    }
}
