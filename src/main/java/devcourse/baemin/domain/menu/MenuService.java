package devcourse.baemin.domain.menu;

import devcourse.baemin.domain.value.Amount;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public MenuDto.ResponseDto addMenu(UUID storeId, MenuDto.AdditionDto menuAdditionDto) {
        Menu menu = toEntity(storeId, menuAdditionDto);
        menuRepository.save(menu);
        return findMenuById(menu.getMenuId());
    }

    public List<MenuDto.ResponseDto> findAllMenu() {
        return menuRepository.findAll()
                .stream()
                .map(MenuDto.ResponseDto::new)
                .collect(Collectors.toList());
    }

    private MenuDto.ResponseDto findMenuById(UUID menuId) {
        Menu foundMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("No menu exists for menuId={0}", menuId)
                ));

        return new MenuDto.ResponseDto(foundMenu);
    }

    private Menu toEntity(UUID storeId, MenuDto.AdditionDto menuAdditionDto) {
        return new Menu(
                UUID.randomUUID(),
                menuAdditionDto.getMenuName(),
                new Amount(menuAdditionDto.getPrice()),
                menuAdditionDto.getDescription(),
                storeId
        );
    }

    public MenuDto.ResponseDto deleteMenu(UUID menuId) {
        MenuDto.ResponseDto menuResponseDto = findMenuById(menuId);
        menuRepository.delete(menuId);
        return menuResponseDto;
    }
}
