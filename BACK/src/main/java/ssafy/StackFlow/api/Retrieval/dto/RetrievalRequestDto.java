package ssafy.StackFlow.api.Retrieval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssafy.StackFlow.api.RT.dto.RtInstructionDto;

import java.util.List;

@Data
@NoArgsConstructor
public class RetrievalRequestDto {
    private List<RetrievalInstructionDto> instructions;
}

