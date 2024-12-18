package ssafy.StackFlow.api.RT.dto.RT;

import lombok.Data;
import ssafy.StackFlow.Domain.product.Color;

@Data
public class RtColorDto {
    private String color_code;

    public RtColorDto(Color color) {
        color_code = color.getColorCode();
    }
}
