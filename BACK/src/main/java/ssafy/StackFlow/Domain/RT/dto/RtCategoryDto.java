package ssafy.StackFlow.Domain.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.category.entity.Category;

@Data
public class RtCategoryDto {
    private String category_code;

    public RtCategoryDto(Category category) {
        category_code = category.getCateCode();
    }
}
