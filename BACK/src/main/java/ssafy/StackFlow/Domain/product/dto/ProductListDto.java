package ssafy.StackFlow.Domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductListDto {
    private Long id;
    private String prodName;
    private String prodCode;
    private String prodDetail;

    private int stockPrice;
    private int sellPrice;
    private int stockQuantity;

    private CategoryDto prodCate;   // DTO 적용
    private ColorDto colorCode;
    private SizeDto size;
    private CategoryGroupDto cateGroup;  // DTO 적용
}
