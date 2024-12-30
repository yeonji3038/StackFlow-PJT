package ssafy.StackFlow.api.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Size;

@Data
public class FilteringDto {
    private String category_code;
    private String category_group_code;
    private String size;
    private String color_code;
    private String store_name;

    public FilteringDto(Color color, CategoryGroup categoryGroup, Category category, Size sizes, Store store) {
        color_code = color.getColorCode();
        category_group_code = categoryGroup.getGroupName();
        category_code = category.getCateCode();
        size = sizes.getSize();
        store_name = store.getStoreName();
    }
}
