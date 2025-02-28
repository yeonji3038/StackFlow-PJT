package ssafy.StackFlow.Domain.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.store.entity.Store;

@Data
public class RtStoreDto {
    private String store_name;

    public RtStoreDto(Store store) {
        store_name = store.getStoreName();
    }
}
