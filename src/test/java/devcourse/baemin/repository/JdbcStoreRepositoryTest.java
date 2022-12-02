package devcourse.baemin.repository;

import devcourse.baemin.domain.Store;
import devcourse.baemin.domain.value.Amount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcStoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("관리자는 가게를 등록할 수 있다.")
    void testSave() {
        // given
        Store store = new Store("store1", new Amount(15000L));

        // when
        storeRepository.save(store);

        // then
        List<Store> stores = storeRepository.findAll();
        assertThat(stores).isNotEmpty();
    }
}