package devcourse.baemin.domain.store;

import devcourse.baemin.global.value.Amount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository {

    void save(Store store);

    List<Store> findAll();

    Optional<Store> findById(UUID storeId);

    void updateMinimumOrderAmount(UUID storeId, Amount updateAmount);
}
