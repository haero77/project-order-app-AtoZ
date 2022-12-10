package devcourse.baemin.domain.store;

import devcourse.baemin.global.value.Amount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcStoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("가게를 저장할 수 있다.")
    void testSave() {
        // given
        Store store = new Store(UUID.randomUUID(), "store1", new Amount(15000L));

        // when
        storeRepository.save(store);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).isNotEmpty();
    }

    @Test
    @DisplayName("가게를 ID로 조회할 수 있다.")
    void testFindById() {
        // given
        Store store = new Store(UUID.randomUUID(), "store1", new Amount(15000L));
        storeRepository.save(store);

        // when
        Store foundStore = storeRepository
                .findById(store.getStoreId())
                .get();

        // then
        assertThat(foundStore)
                .usingRecursiveComparison()
                .isEqualTo(store);
    }
    
    @Test
    @DisplayName("가게의 최소주문금액을 변경할 수 있다.")
    void testUpdateMinimumOrderAmount() {
        // given
        Store store = new Store(UUID.randomUUID(), "store1", new Amount(15000L));
        storeRepository.save(store);
        Amount newMinimumOrderAmount = new Amount(30000L);
        
        // when
        storeRepository.updateMinimumOrderAmount(store.getStoreId(), newMinimumOrderAmount);
        
        // then
        Store foundStore = storeRepository.findById(store.getStoreId()).get();
        long expected = newMinimumOrderAmount.getAmount();
        long actual = foundStore.getMinimumOrderAmount();
        assertThat(actual).isEqualTo(expected);
    }
}