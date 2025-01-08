package ssafy.StackFlow.api.Retrieval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreStockDto {
    private Long storeId;
    private String storeName;
    private int stockQuantity;
}

