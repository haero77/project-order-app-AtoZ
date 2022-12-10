package devcourse.baemin.domain.menu;

import devcourse.baemin.global.value.Amount;
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
        Menu menu = toMenu(storeId, menuAdditionDto);
        menuRepository.save(menu);
        return getMenuAsDto(menu.getMenuId());
    }

    public List<MenuDto.ResponseDto> findAllMenu() {
        return menuRepository.findAll()
                .stream()
                .map(MenuDto.ResponseDto::new)
                .collect(Collectors.toList());
    }

    public MenuDto.ResponseDto getMenuAsDto(UUID menuId) {
        return new MenuDto.ResponseDto(findMenuById(menuId));
    }

    public MenuDto.ResponseDto deleteMenu(UUID menuId) {
        MenuDto.ResponseDto menuResponseDto = getMenuAsDto(menuId);
        menuRepository.delete(menuId);
        return menuResponseDto;
    }

    public Menu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("No menu exists for menuId={0}", menuId)
                ));
    }

    private Menu toMenu(UUID storeId, MenuDto.AdditionDto menuAdditionDto) {
        return new Menu(
                UUID.randomUUID(),
                menuAdditionDto.getMenuName(),
                new Amount(menuAdditionDto.getPrice()),
                menuAdditionDto.getDescription(),
                storeId
        );
    }
}
