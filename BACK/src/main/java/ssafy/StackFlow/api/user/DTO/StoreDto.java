package ssafy.StackFlow.api.user.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreDto {
    private Long id;
    private String storeName;
    private String location;
    private String storeCode;
}