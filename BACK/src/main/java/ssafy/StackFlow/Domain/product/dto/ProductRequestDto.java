package ssafy.StackFlow.Domain.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductRequestDto {
    private List<ProductCreateDto> create;
}

