package ssafy.StackFlow.api.RT.dto.RT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RtResponseDto {
    private String status;
    private Object data;
}
