package ssafy.StackFlow.Domain.Retrieval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RetrievalRequestDto {
    private List<RetrievalInstructionDto> instructions;
}

