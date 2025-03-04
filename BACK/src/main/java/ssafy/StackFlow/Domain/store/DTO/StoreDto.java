package ssafy.StackFlow.Domain.store.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ssafy.StackFlow.Domain.store.entity.Store;
import ssafy.StackFlow.Domain.user.DTO.UserDto;
import ssafy.StackFlow.Domain.user.entity.Role;
import ssafy.StackFlow.Domain.user.entity.Signup;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private Long id;               // 매장 ID
    private String storeName;      // 매장 이름
    private String location;       // 매장 위치
    private Timestamp createdAt;

    public static Store toEntity(StoreDto storeDto) {
        return Store.builder()
                .storeName(storeDto.getStoreName())
                .location(storeDto.getLocation())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .storeName(store.getStoreName())
                .location(store.getLocation())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
