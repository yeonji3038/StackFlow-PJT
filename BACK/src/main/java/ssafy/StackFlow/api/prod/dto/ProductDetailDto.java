package ssafy.StackFlow.api.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ssafy.StackFlow.Domain.category.Category;
import ssafy.StackFlow.Domain.category.CategoryGroup;
import ssafy.StackFlow.Domain.product.Color;
import ssafy.StackFlow.Domain.product.Size;

@Data
@AllArgsConstructor
public class ProductDetailDto {
    private Long id;
    private String productName;
    private String productCode;
    private String productDetail;

    private int stockPrice;
    private int stockQuantity;
    private int sellPrice;

    private CategoryDto prodCate;   // DTO 적용
    private ColorDto colorCode;
    private SizeDto size;
    private CategoryGroupDto cateGroup;  // DTO 적용
}
