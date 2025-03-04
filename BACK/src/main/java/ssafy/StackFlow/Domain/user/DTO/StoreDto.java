package ssafy.StackFlow.Domain.user.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private Long id;
    private String storeName;
    private String location;
    private String storeCode;
}