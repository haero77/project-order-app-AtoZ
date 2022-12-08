package devcourse.baemin.domain.menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {

    void save(Menu menu);

    Optional<Menu> findById(UUID menuId);

    List<Menu> findAll();

    void delete(UUID menuId);
}
