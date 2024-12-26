package ssafy.StackFlow.api.RT.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RtResponseDto {
    private String status;
    private Object data;
}
