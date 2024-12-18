package ssafy.StackFlow.api.RT.dto.RT;

import lombok.Data;
import ssafy.StackFlow.Domain.product.Size;

@Data
public class RtSizeDto {
    private String size;

    public RtSizeDto(Size sizes) {
        size = sizes.getSize();
    }
}
