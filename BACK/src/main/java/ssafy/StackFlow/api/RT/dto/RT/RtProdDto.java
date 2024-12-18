package ssafy.StackFlow.api.RT.dto.RT;

import lombok.Data;
import ssafy.StackFlow.Domain.product.Product;

@Data
public class RtProdDto{
    private Long prod_id;
    private String product_code;
    private String product_name;
    private String color_code;
    private String product_size;

    public RtProdDto(Product product) {
        prod_id = product.getId();
        product_code = product.getProdCode();
        product_name = product.getProdName();
        color_code = product.getColorCode().getColorCode();
        product_size = product.getSize().getSize();
    }
}