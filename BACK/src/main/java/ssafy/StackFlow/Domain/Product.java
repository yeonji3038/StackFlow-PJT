package ssafy.StackFlow.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    private String prodName;
    private String prodCode;

    @OneToOne
    @JoinColumn(name = "prodCategory")
    private Category prodCate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "prod_color")
    private Color colorCode;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "prod_size")
    private Size size;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Store> stores = new ArrayList<>();
}
