package devcourse.baemin.domain.menu;

import devcourse.baemin.domain.store.Store;
import devcourse.baemin.domain.store.StoreRepository;
import devcourse.baemin.domain.value.Amount;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcMenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private StoreRepository storeRepository;

    private Store store;

    @BeforeEach
    void beforeEach() {
        store = new Store(UUID.randomUUID(), "test-store", new Amount(10000L));
        storeRepository.save(store);
    }

    @Test
    @DisplayName("메뉴를 저장할 수 있다.")
    void testSave() {
        // given
        Menu menu1 = new Menu(UUID.randomUUID(), "test-menu1", new Amount(20000L), store.getStoreId());
        Menu menu2 = new Menu(UUID.randomUUID(), "test-menu2", new Amount(20000L), "Yummy Menu!", store.getStoreId());

        // when
        menuRepository.save(menu1);
        menuRepository.save(menu2);

        // then
        List<Menu> menus = menuRepository.findAll();
        int actualSize = menus.size();
        int expectedSize = 2;

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    @DisplayName("메뉴를 ID로 조회할 수 있다.")
    void testFindById() {
        // given
        Menu menu = new Menu(UUID.randomUUID(), "test-menu", new Amount(20000L), store.getStoreId());
        menuRepository.save(menu);

        // when
        Optional<Menu> foundMenu = menuRepository.findById(menu.getMenuId());

        // then
        assertThat(foundMenu).isPresent();
        log.info("menu description={}", foundMenu.get().getDescription());
    }

    @Test
    @DisplayName("메뉴를 ID로 삭제할 수 있다.")
    void testDelete() {
        // given
        Menu menu = new Menu(UUID.randomUUID(), "test-menu", new Amount(20000L), store.getStoreId());
        menuRepository.save(menu);

        // when
        menuRepository.delete(menu.getMenuId());

        // then
        List<Menu> menus = menuRepository.findAll();
        assertThat(menus).isEmpty();
    }
}