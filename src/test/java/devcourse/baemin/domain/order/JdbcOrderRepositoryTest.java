package devcourse.baemin.domain.order;

import devcourse.baemin.domain.member.Member;
import devcourse.baemin.domain.member.MemberRepository;
import devcourse.baemin.domain.menu.Menu;
import devcourse.baemin.domain.menu.MenuRepository;
import devcourse.baemin.domain.store.Store;
import devcourse.baemin.domain.store.StoreRepository;
import devcourse.baemin.domain.value.Amount;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JdbcOrderRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private OrderRepository orderRepository;

    private Member member;
    private Store store;
    private Menu menu;

    @BeforeEach
    void beforeEach() {
        Member member = new Member("member", "member!", "member-name");
        memberRepository.save(member);

        Store store = new Store(UUID.randomUUID(), "store-name", new Amount(15000L));
        storeRepository.save(store);

        Menu menu = new Menu(UUID.randomUUID(), "menu-name", new Amount(20000L), store.getStoreId());
        menuRepository.save(menu);

        this.member = member;
        this.store = store;
        this.menu = menu;
    }

    @Test
    void save() {
        // given
        Order order = new Order(
                UUID.randomUUID(),
                member.getMemberId(),
                menu.getMenuId(),
                store.getStoreId(),
                3L,
                new Amount(30000L),
                OrderStatus.PAID,
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()
        );

        // when
        orderRepository.save(order);

        // then
        List<Order> orders = orderRepository.findAll();
        Optional<Order> foundOrder = orderRepository.findById(order.getOrderId());

        assertAll(
                () -> assertThat(orders).isNotEmpty(),
                () -> assertThat(foundOrder).isPresent()
        );
    }

    @Test
    void test_updateCancelledAt() {
        // given
        Order order = new Order(
                UUID.randomUUID(),
                member.getMemberId(),
                menu.getMenuId(),
                store.getStoreId(),
                3L,
                new Amount(30000L),
                OrderStatus.PAID,
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime()
        );
        orderRepository.save(order);
        order.cancel();

        // when
        orderRepository.updateCancelledAt(order.getOrderId(), order.getCancelledAt());

        // then
        Order foundOrder = orderRepository.findById(order.getOrderId()).get();
        LocalDateTime actual = foundOrder.getCancelledAt();
        LocalDateTime expected = order.getCancelledAt();

        assertThat(actual).isEqualTo(expected);
    }
}