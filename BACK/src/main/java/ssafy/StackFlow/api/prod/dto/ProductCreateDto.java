package ssafy.StackFlow.api.prod.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCreateDto {
    private String product_name;
    private String product_detail;
    private String product_code;
    private String category_group;
    private String category_code;
    private String color_code;
    private String size;
    private int stock_price;
    private int stock_quantity;
    private int sell_price;
}
