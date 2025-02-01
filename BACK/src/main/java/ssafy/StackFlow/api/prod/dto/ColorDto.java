package ssafy.StackFlow.api.prod.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties({"products"})  // 🚀 무한 참조 방지
public class ColorDto {
    private Long id;
    private String colorName;
    private String colorCode;
}
