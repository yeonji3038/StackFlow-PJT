package ssafy.StackFlow.api.prod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductUpdateDto {
    private String prodName;
    private String prodDetail;
    private String prodCode;

    private int stockPrice;
    private int sellPrice;
    private int stockQuantity;

    private Long categoryId;
    private Long categoryGroupId;
    private Long colorId;
    private Long sizeId;
}
