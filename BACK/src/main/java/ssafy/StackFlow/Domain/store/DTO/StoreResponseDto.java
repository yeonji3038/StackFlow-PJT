package ssafy.StackFlow.Domain.store.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ssafy.StackFlow.Domain.store.entity.Store;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponseDto {
    private Long id;
    private String storeName;
    private String storeCode;
    private Timestamp createdAt;

    public static StoreResponseDto fromEntity(Store store) {
        return StoreResponseDto.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .storeCode(store.getStoreCode())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
