package ssafy.StackFlow.Domain.Retrieval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RetrievalInstructionUserDto {
    private Long productId;
    private int retrivalQuantity;
}

