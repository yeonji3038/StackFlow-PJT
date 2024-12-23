package ssafy.StackFlow.api.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.category.Category;

@Data
public class RtCategoryDto {
    private String category_code;

    public RtCategoryDto(Category category) {
        category_code = category.getCateCode();
    }
}
