package ssafy.StackFlow.api.Retrieval.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.product.Product;

@Data
public class RetrievalProdDto {
    private Long prod_id;
    private String product_code;
    private String product_name;
    private String color_code;
    private String product_size;
    private int headOfficeStock;
    private int storeStock;
    private String category_group;
    private String category_code;

    public RetrievalProdDto(Product product, ProductStockDto productStockDto) {
        prod_id = product.getId();
        product_code = product.getProdCode();
        product_name = product.getProdName();
        color_code = product.getColorCode().getColorCode();
        product_size = product.getSize().getSize();
        category_group = product.getCateGroup().getGroupName();
        category_code = product.getProdCate().getCateCode();
        this.headOfficeStock = productStockDto.getHeadOfficeStock();
        this.storeStock = productStockDto.getStoreStock();
    }
}
