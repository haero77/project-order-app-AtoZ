package devcourse.baemin.api;

import devcourse.baemin.domain.menu.MenuDto;
import devcourse.baemin.domain.menu.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
public class MenuApiController {

    private final MenuService menuService;

    public MenuApiController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuDto.ResponseDto>> getMenus() {
        List<MenuDto.ResponseDto> menus = menuService.findAllMenu();
        return ResponseEntity.ok().body(menus);
    }

    @PostMapping("/{storeId}")
    public ResponseEntity<MenuDto.ResponseDto> addMenu(
            @PathVariable UUID storeId, @RequestBody MenuDto.AdditionDto menuAdditionDto
    ) {
        MenuDto.ResponseDto menuResponseDto = menuService.addMenu(storeId, menuAdditionDto);
        return ResponseEntity.ok().body(menuResponseDto);
    }



/*
    @GetMapping
    public DefaultResponse<?> getMenus() {
        List<Menu> menus = menuService.findAllMenu();
        return DefaultResponse.response(HttpStatus.OK.value(), )
    }
*/

/*    @GetMapping("/menus/{menuId}")
    public DefaultResponse<?> menuDetail(@PathVariable UUID menuId) {
        menuService.findMenuById(menuId);
        return DefaultResponse.response(HttpStatus.OK.value())
    }*/

}
