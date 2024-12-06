package ssafy.StackFlow.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String prodCode;
    private String prodName;
    private String size;
    private String colorCode;
    private int respQuan;
    private String store;
}
