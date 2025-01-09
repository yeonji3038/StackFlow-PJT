package ssafy.StackFlow.api.Retrieval.dto;

import lombok.Data;
import ssafy.StackFlow.Domain.product.Product;

import java.util.List;

@Data
public class RetrievalProdDto {
    private Long prod_id;
    private int sell_price;
    private int stock_price;
    private String product_code;
    private String product_name;
    private String color_code;
    private String product_size;
    private int headOfficeStock;
    private List<StoreStockDto> storeStocks;
    private String category_group;
    private String category_code;

    public RetrievalProdDto(Product product, int headOfficeStock, List<StoreStockDto> storeStocks) {
        prod_id = product.getId();
        product_code = product.getProdCode();
        sell_price = product.getSellPrice();
        stock_price = product.getStockPrice();
        product_name = product.getProdName();
        color_code = product.getColorCode().getColorCode();
        product_size = product.getSize().getSize();
        category_group = product.getCateGroup().getGroupName();
        category_code = product.getProdCate().getCateCode();
        this.headOfficeStock = headOfficeStock;
        this.storeStocks = storeStocks;
    }
}

