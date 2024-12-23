package ssafy.StackFlow.api.RT.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RtRequestDto {
    private List<RtInstructionDto> instructions;
}
