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
    private Long id;

    private String prodCode;
    private String prodName;
    private String size;
    private String colorCode;
    private int respQuan;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private List<Store> stores = new ArrayList<>();
}
