package ssafy.StackFlow.Domain.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties({"cateGroup"}) // 🚀 무한 참조 방지
public class CategoryDto {
    private Long id;
    private String cateCode;
    private String cateName;
}
