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
    private String storeName;  //매장 이름
    private String storeCode;  //매장 코드
    private Timestamp createdAt; //생성일

    public static StoreResponseDto fromEntity(Store store) {
        return StoreResponseDto.builder()
                .storeName(store.getStoreName())
                .storeCode(store.getStoreCode())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
