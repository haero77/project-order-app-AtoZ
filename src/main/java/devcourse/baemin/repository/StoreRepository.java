package devcourse.baemin.repository;

import devcourse.baemin.domain.Store;

import java.util.List;

public interface StoreRepository {

    void save(Store store);

    List<Store> findAll();
}
