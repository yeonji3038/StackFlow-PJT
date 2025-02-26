package ssafy.StackFlow.Domain.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.product.entity.Size;

@Data
public class RtSizeDto {
    private String size;

    public RtSizeDto(Size sizes) {
        size = sizes.getSize();
    }
}
