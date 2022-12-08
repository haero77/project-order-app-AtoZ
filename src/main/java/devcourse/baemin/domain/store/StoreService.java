package devcourse.baemin.domain.store;

import devcourse.baemin.domain.value.Amount;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public StoreDto.ResponseDto addStore(StoreDto.RequestDto requestDto) {
        Store store = new Store(
                UUID.randomUUID(),
                requestDto.getStoreName(),
                new Amount(requestDto.getMinimumOrderAmount())
        );
        storeRepository.save(store);
        return new StoreDto.ResponseDto(store);
    }

    public List<StoreDto.ResponseDto> findAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(StoreDto.ResponseDto::new)
                .collect(Collectors.toList());
    }

    public StoreDto.ResponseDto updateMinimumOrderAmount(UUID storeId, long minimumOrderAmount) {
        storeRepository.updateMinimumOrderAmount(storeId, new Amount(minimumOrderAmount));
        Store updatedStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("Can't find store for storeId={0}", storeId)
                ));
        return new StoreDto.ResponseDto(updatedStore);
    }
}
