package ssafy.StackFlow.Domain.RT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.product.entity.Product;

import static jakarta.persistence.FetchType.LAZY;
@Entity
@Getter @Setter
public class RtProduct {

    @Id @GeneratedValue
    @Column(name = "RT_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RT_id")
    private RT RT;

    private int reqQuant;

    public static RtProduct createRtProduct(Product product, int reqQuant) {
        RtProduct rtProduct = new RtProduct();
        rtProduct.setProduct(product);
        rtProduct.setReqQuant(reqQuant);
        return rtProduct;
    }  

}