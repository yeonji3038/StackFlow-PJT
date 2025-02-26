package ssafy.StackFlow.Domain.RT.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.product.entity.Product;

@Data
public class RtProdDto{
    private Long prod_id;
    private String product_code;
    private String product_name;
    private String color_code;
    private String product_size;
    private String category_group;
    private String category_code;

    public RtProdDto(Product product) {
        prod_id = product.getId();
        product_code = product.getProdCode();
        product_name = product.getProdName();
        color_code = product.getColorCode().getColorCode();
        product_size = product.getSize().getSize();
        category_group = product.getCateGroup().getGroupName();
        category_code = product.getProdCate().getCateCode();
    }
}