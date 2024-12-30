package ssafy.StackFlow.api.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {
        private String status;
        private Object data;
}

