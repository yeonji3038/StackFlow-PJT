package ssafy.StackFlow.Domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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
