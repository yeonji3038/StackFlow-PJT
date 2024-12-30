package ssafy.StackFlow.Domain.Retrieval;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.product.Product;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class RetrievalProduct {
    @Id
    @GeneratedValue
    @Column(name = "Retrieval_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Retrieval_id")
    private Retrieval Retrieval;

    private int retQuant;

    public static RetrievalProduct createRetrievalProduct(Product product, int retQuant) {
        RetrievalProduct retrievalProduct = new RetrievalProduct();
        retrievalProduct.setProduct(product);
        retrievalProduct.setRetQuant(retQuant);
        return retrievalProduct;
    }
}

