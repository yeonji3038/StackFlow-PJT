package ssafy.StackFlow.Domain.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties({"categories"}) // 🚀 무한 참조 방지
public class CategoryGroupDto {
    private Long id;
    private String groupName;
}
