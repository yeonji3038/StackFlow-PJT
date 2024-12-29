package ssafy.StackFlow.api.prod.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ssafy.StackFlow.api.RT.dto.RtInstructionDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductRequestDto {
    private List<ProductCreateDto> create;
}

