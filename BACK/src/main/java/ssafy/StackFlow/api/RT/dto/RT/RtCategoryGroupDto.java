package ssafy.StackFlow.api.RT.dto.RT;

import lombok.Data;
import ssafy.StackFlow.Domain.category.CategoryGroup;

@Data
public class RtCategoryGroupDto {
    private String category_group_code;

    public RtCategoryGroupDto(CategoryGroup categoryGroup) {
        category_group_code = categoryGroup.getGroupName();
    }
}
