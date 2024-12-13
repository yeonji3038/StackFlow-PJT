package ssafy.StackFlow.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    private String prodCode;
    private String prodName;
    private String size;

    @OneToOne
    @JoinColumn(name = "prodColor")
    private Color colorCode;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Store> stores = new ArrayList<>();
}
