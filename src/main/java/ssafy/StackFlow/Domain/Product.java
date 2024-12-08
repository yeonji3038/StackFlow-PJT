package ssafy.StackFlow.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String colorCode;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Store> stores = new ArrayList<>();
}
