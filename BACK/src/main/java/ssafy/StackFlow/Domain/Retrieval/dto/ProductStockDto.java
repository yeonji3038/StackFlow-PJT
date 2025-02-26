package ssafy.StackFlow.Domain.Retrieval.dto;


import lombok.Data;
import ssafy.StackFlow.Domain.product.entity.Product;

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

