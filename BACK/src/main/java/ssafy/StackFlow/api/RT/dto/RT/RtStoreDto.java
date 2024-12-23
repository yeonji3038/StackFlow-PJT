package ssafy.StackFlow.api.RT.dto.RT;

import lombok.Data;
import ssafy.StackFlow.Domain.Store;

@Data
public class RtStoreDto {
    private String store_name;

    public RtStoreDto(Store store) {
        store_name = store.getStoreName();
    }
}
