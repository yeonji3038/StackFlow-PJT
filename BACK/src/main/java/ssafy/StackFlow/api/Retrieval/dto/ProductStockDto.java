package ssafy.StackFlow.api.Retrieval.dto;


import lombok.Data;
import ssafy.StackFlow.Domain.product.Product;

@Data
public class ProductStockDto {
    private Product product;
    private int headOfficeStock;
    private int storeStock;

    public ProductStockDto(Product product, int headOfficeStock, int storeStock) {
        this.product = product;
        this.headOfficeStock = headOfficeStock;
        this.storeStock = storeStock;
    }


}

