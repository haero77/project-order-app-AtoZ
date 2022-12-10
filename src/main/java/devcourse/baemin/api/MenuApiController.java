package devcourse.baemin.api;

import devcourse.baemin.api.response.CommonResponse;
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
    public ResponseEntity<CommonResponse<List<MenuDto.ResponseDto>>> getMenus() {
        List<MenuDto.ResponseDto> menus = menuService.findAllMenu();
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Found all menus.", menus));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<CommonResponse<MenuDto.ResponseDto>> getMenu(@PathVariable UUID menuId) {
        MenuDto.ResponseDto menuResponseDto = menuService.getMenuAsDto(menuId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Found a menu", menuResponseDto));
    }

    @PostMapping("/{storeId}")
    public ResponseEntity<MenuDto.ResponseDto> addMenu(
            @PathVariable UUID storeId, @RequestBody MenuDto.AdditionDto menuAdditionDto
    ) {
        MenuDto.ResponseDto menuResponseDto = menuService.addMenu(storeId, menuAdditionDto);
        return ResponseEntity.ok().body(menuResponseDto);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<CommonResponse<MenuDto.ResponseDto>> deleteMenu(@PathVariable UUID menuId) {
        MenuDto.ResponseDto menuResponseDto = menuService.deleteMenu(menuId);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Menu Deleted", menuResponseDto));
    }
}
