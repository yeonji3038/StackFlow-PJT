package ssafy.StackFlow.api.Retrieval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RetrievalInstructionDto {
    private Long productId;
    private Long storeId;
    private int retrivalQuantity;
}

