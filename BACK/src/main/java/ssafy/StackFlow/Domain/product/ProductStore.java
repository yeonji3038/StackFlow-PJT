package ssafy.StackFlow.Domain.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;

@Entity
@Getter @Setter
public class ProductStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int stockQuantity;
}
