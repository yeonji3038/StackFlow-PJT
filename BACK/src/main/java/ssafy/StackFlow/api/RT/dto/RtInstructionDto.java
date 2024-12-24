package ssafy.StackFlow.api.RT.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RtInstructionDto {
    private Long productId;
    private Long storeId;
    private int requestQuantity;
}
