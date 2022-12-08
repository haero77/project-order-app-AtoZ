package devcourse.baemin.api;

import devcourse.baemin.domain.store.StoreDto;
import devcourse.baemin.domain.store.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
public class StoreApiController {

    private final StoreService storeService;

    public StoreApiController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<List<StoreDto.ResponseDto>> getAllStores() {
        List<StoreDto.ResponseDto> stores = storeService.findAllStores();
        return ResponseEntity.ok().body(stores);
    }

    @PostMapping
    public ResponseEntity<StoreDto.ResponseDto> addStore(@RequestBody StoreDto.RequestDto requestDto) {
        StoreDto.ResponseDto responseDto = storeService.addStore(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreDto.ResponseDto> updateMinimumOrderAmount(
            @PathVariable UUID storeId,
            @RequestBody Map<String, Long> minimumOrderAmount
    ) {
        StoreDto.ResponseDto responseDto = storeService.updateMinimumOrderAmount(
                storeId, minimumOrderAmount.get("minimumOrderAmount")
        );
        return ResponseEntity.ok().body(responseDto);
    }
}
