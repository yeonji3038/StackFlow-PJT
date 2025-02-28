package ssafy.StackFlow.Domain.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.category.entity.CategoryGroup;

@Data
public class RtCategoryGroupDto {
    private String category_group_code;

    public RtCategoryGroupDto(CategoryGroup categoryGroup) {
        category_group_code = categoryGroup.getGroupName();
    }
}
